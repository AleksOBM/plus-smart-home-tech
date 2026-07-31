package ru.yandex.practicum.order.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.order.dto.*;
import ru.yandex.practicum.order.dto.data.OrderData;
import ru.yandex.practicum.order.dto.data.OrderItemData;
import ru.yandex.practicum.order.dto.feign.ProductDto;
import ru.yandex.practicum.order.dto.feign.ReserveRequest;
import ru.yandex.practicum.order.dto.feign.ReserveResponse;
import ru.yandex.practicum.order.exception.ExceptionMapper;
import ru.yandex.practicum.order.exception.OrderProcessingException;
import ru.yandex.practicum.order.feign.InventoryClient;
import ru.yandex.practicum.order.feign.ProductClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderOrchestrationServiceImpl implements OrderOrchestrationService {

	private final OrderService orderService;
	private final ProductClient productClient;
	private final InventoryClient inventoryClient;

	@Override
	public OrderDto createOrder(@NonNull CreateOrderRequest request) {

		log.debug("Инициализация запроса");
		var productIdToQuantity = new HashMap<Long, Integer>();
		request.items().forEach(itemRequest -> productIdToQuantity
				.merge(itemRequest.productId(), itemRequest.quantity(), Integer::sum)
		);

		var items = new ArrayList<OrderItemData>();

		log.debug("Резервирование товаров");
		var reserveRequests = new ArrayList<ReserveRequest>();
		for (Map.Entry<Long, Integer> product : productIdToQuantity.entrySet()) {
			long productId = product.getKey();
			int quantity = product.getValue();

			ProductDto productDto = getProductById(productId);
			if (!productDto.active()) {
				log.debug("Товар с id={} товар снят с продажи", productId);
				if (!reserveRequests.isEmpty()) {
					log.debug("Отмена резервирования товаров");
					for (ReserveRequest reserveRequest : reserveRequests) {
						ReserveResponse reserveResponse = releaseReservedProduct(reserveRequest);
						log.debug("Резервирование товара отменено: {}", reserveResponse);
					}
				}
				throw new OrderProcessingException(
						String.format("Товар с id=%s товар снят с продажи", productId)
				);
			}

			ReserveRequest reserveRequest = new ReserveRequest(
					productId,
					quantity
			);
			ReserveResponse reserveResponse = reserveProduct(reserveRequest);
			reserveRequests.add(reserveRequest);
			items.add(OrderItemData.builder()
					.productId(productId)
					.productName(productDto.name())
					.quantity(quantity)
					.price(productDto.price())
					.build()
			);
			log.debug("Товар успешно зарезервирован: {}", reserveResponse);
		}

		log.debug("Инициализация данных заказа");
		OrderData orderData = OrderData.builder()
				.customerEmail(request.customerEmail())
				.customerName(request.customerName())
				.items(items)
				.build();

		log.debug("Регистрация заказа");
		return orderService.createOrder(orderData);
	}

	private ReserveResponse reserveProduct(ReserveRequest request) {
		try {
			return inventoryClient.reserveProduct(request);
		} catch (FeignException e) {
			throw ExceptionMapper.mapInventoryException(e)
					.orElseThrow(() ->
							new RuntimeException(
									"Не удалось зарезервировать товар с id=%s"
											.formatted(request.productId())
							)
					);
		}
	}

	private ReserveResponse releaseReservedProduct(ReserveRequest request) {
		try {
			return inventoryClient.releaseProduct(request);
		} catch (FeignException e) {
			throw ExceptionMapper.mapInventoryException(e)
					.orElseThrow(() ->
							new RuntimeException(
									"Не удалось отменить резервирование товара с id=%s"
											.formatted(request.productId())
							)
					);
		}
	}

	private ProductDto getProductById(long productId) {
		try {
			return productClient.getProductById(productId);
		} catch (FeignException e) {
			throw ExceptionMapper.mapProductException(e)
					.orElseThrow(() ->
							new RuntimeException(
									"Не удалось получить данные товара с id=%s"
											.formatted(productId)
							)
					);
		}
	}
}