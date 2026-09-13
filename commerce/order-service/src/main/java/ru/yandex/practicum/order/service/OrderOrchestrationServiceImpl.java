package ru.yandex.practicum.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.order.dto.CreateOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.dto.data.OrderContext;
import ru.yandex.practicum.order.dto.data.OrderData;
import ru.yandex.practicum.order.dto.data.OrderItemData;
import ru.yandex.practicum.order.dto.feign.ProductDto;
import ru.yandex.practicum.order.dto.feign.ReserveRequest;
import ru.yandex.practicum.order.dto.feign.ReserveResponse;
import ru.yandex.practicum.order.entity.OrderStatus;
import ru.yandex.practicum.order.exception.OrderProcessingException;
import ru.yandex.practicum.order.exception.ServiceUnavailableException;
import ru.yandex.practicum.order.feign.InventoryClient;
import ru.yandex.practicum.order.feign.ProductClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderOrchestrationServiceImpl implements OrderOrchestrationService {

	private final OrderService orderService;
	private final ProductClient productClient;
	private final InventoryClient inventoryClient;

	@Override
	public OrderDto createOrder(@NonNull CreateOrderRequest request) {

		log.trace("Инициализация запроса");
		var productIdToQuantity = new HashMap<Long, Integer>();
		request.items().forEach(itemRequest -> productIdToQuantity
				.merge(itemRequest.productId(), itemRequest.quantity(), Integer::sum)
		);
		var items = new ArrayList<OrderItemData>();

		log.info("Резервирование товаров: {}", productIdToQuantity);
		var reservationRequests = new ArrayList<ReserveRequest>();
		for (Map.Entry<Long, Integer> product : productIdToQuantity.entrySet()) {

			log.trace("Инициализация контекста");
			OrderContext context = OrderContext.builder()
					.productId(product.getKey())
					.customerName(request.customerName())
					.customerEmail(request.customerEmail())
					.quantity(product.getValue())
					.build();

			log.debug("Получение данных товара из product-service");
			ProductDto productDto = getProduct(context, reservationRequests);
			if (productDto == null) {
				context = context.toBuilder()
						.productName("Товар #%s (ожидает проверки)".formatted(product.getKey()))
						.price(BigDecimal.ZERO)
						.build();
				addProductToOrder(context, items);

				return savePendingOrder(context, items);
			}

			context = context.toBuilder()
					.productName(productDto.name())
					.price(productDto.price())
					.build();

			addProductToOrder(context, items);

			log.debug("Резервирование товара с id={}", product.getKey());
			ReserveResponse response = reserveProduct(context, reservationRequests);
			if (response == null) {
				return savePendingOrder(context, items);
			}
		}

		log.trace("Инициализация данных заказа");
		OrderData orderData = OrderData.builder()
				.customerName(request.customerName())
				.customerEmail(request.customerEmail())
				.status(OrderStatus.CONFIRMED)
				.items(items)
				.build();

		log.debug("Регистрация заказа");
		return orderService.createOrder(orderData);
	}

	private void addProductToOrder(@NonNull OrderContext context,
	                               @NonNull Collection<OrderItemData> items) {
		log.trace("Добавление товара с id={} в заказ", context.productId());
		items.add(OrderItemData.builder()
				.productId(context.productId())
				.productName(context.productName())
				.quantity(context.quantity())
				.price(context.price())
				.build()
		);
	}

	private OrderDto savePendingOrder(@NonNull OrderContext context,
	                                  Collection<OrderItemData> items) {
		log.trace("Инициализация данных заказа со статусом PENDING");
		OrderData orderData = OrderData.builder()
				.customerName(context.customerName())
				.customerEmail(context.customerEmail())
				.status(OrderStatus.PENDING_CONFIRMATION)
				.statusDetails("Заказ требует ручной проверки")
				.items(items)
				.build();

		log.debug("Сохранение заказа со статусом PENDING");
		return orderService.createOrder(orderData);
	}

	@Nullable
	private ProductDto getProduct(OrderContext context,
	                              Collection<ReserveRequest> requests) {
		ProductDto productDto;
		var productCallResult = executeRemoteCall(
				() -> productClient.getProductById(context.productId())
		);
		switch (productCallResult) {
			case RemoteCallResult.Success(var value) -> productDto = value;
			case RemoteCallResult.Failure(var message) -> {
				log.warn("Получено бизнес-исключение при получении заказа. Заказ отклонен.");
				throw new OrderProcessingException(message);
			}
			case RemoteCallResult.Degraded(var cause) -> {
				log.warn("Сервис товаров недоступен. {}", cause.getMessage());
				if (!requests.isEmpty()) {
					releaseReservedProducts(requests);
				}
				return null;
			}
		}

		log.debug("Проверка товара");
		if (!productDto.active()) {
			log.debug("Товар с id={} снят с продажи", context.productId());
			if (!requests.isEmpty()) {
				releaseReservedProducts(requests);
			}
			throw new OrderProcessingException(
					"Товар с id=%s снят с продажи".formatted(context.productId())
			);
		}

		return productDto;
	}

	@Nullable
	private ReserveResponse reserveProduct(@NonNull OrderContext context,
	                                       Collection<ReserveRequest> requests) {
		ReserveRequest request = new ReserveRequest(
				context.productId(),
				context.quantity()
		);

		ReserveResponse reserveResponse;
		var reserveCallResult = executeRemoteCall(
				() -> inventoryClient.reserveProduct(request)
		);
		switch (reserveCallResult) {
			case RemoteCallResult.Success(var response) -> {
				requests.add(request);
				reserveResponse = response;
				log.debug("Товар успешно зарезервирован: {}", response);
			}
			case RemoteCallResult.Failure(var message) -> {
				log.warn("Получено бизнес-исключение при резервировании заказа. Заказ отклонен.");
				throw new OrderProcessingException(message);
			}
			case RemoteCallResult.Degraded(var cause) -> {
				log.warn("Сервис склада недоступен. Сохранение заказа. {}", cause.getMessage());
				if (!requests.isEmpty()) {
					releaseReservedProducts(requests);
				}

				return null;
			}
		}

		return reserveResponse;
	}

	private void releaseReservedProducts(@NonNull Collection<ReserveRequest> requests) {
		log.debug("Отмена резервирования товаров {}", requests);
		requests.forEach(this::releaseReservation);
	}

	private void releaseReservation(ReserveRequest request) {
		try {
			switch (executeRemoteCall(() -> inventoryClient.releaseProduct(request))) {

				case RemoteCallResult.Success(var response) ->
						log.debug("Резервирование товара {} отменено: {}",
								request.productId(), response);

				case RemoteCallResult.Failure(var message) -> log.warn(
						"Не удалось отменить резерв товара {}: {}",
						request.productId(),
						message
				);

				case RemoteCallResult.Degraded(var ex) -> log.warn(
						"Сервис склада недоступен при отмене резерва товара {}",
						request.productId(),
						ex
				);
			}
		} catch (Exception ex) {
			log.error(
					"Неожиданная ошибка при отмене резерва товара {}",
					request.productId(),
					ex
			);
		}
	}

	@NonNull
	private <T> RemoteCallResult<T> executeRemoteCall(@NonNull Supplier<T> action) {
		try {
			return new RemoteCallResult.Success<>(action.get());

		} catch (OrderProcessingException ex) {
			return new RemoteCallResult.Failure<>(ex.getMessage());

		} catch (ServiceUnavailableException ex) {
			return new RemoteCallResult.Degraded<>(ex);
		}
	}
}