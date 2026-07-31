package ru.yandex.practicum.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.order.dto.data.OrderData;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.entity.Order;
import ru.yandex.practicum.order.entity.OrderStatus;
import ru.yandex.practicum.order.exception.NotFoundException;
import ru.yandex.practicum.order.mapper.OrderItemMapper;
import ru.yandex.practicum.order.mapper.OrderMapper;
import ru.yandex.practicum.order.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Override
	public List<OrderDto> getAllOrders() {
		return orderRepository.findAll().stream()
				.map(OrderMapper::toDto)
				.toList();
	}

	@Override
	public OrderDto getOrderById(Long id) {
		return OrderMapper.toDto(orderRepository.findById(id)
				.orElseThrow(() ->
						new NotFoundException(String.format("Заказ с id %s не найден", id))
				)
		);
	}

	@Override
	public List<OrderDto> getOrdersByEmail(String email) {
		return orderRepository.findByCustomerEmail(email).stream()
				.map(OrderMapper::toDto)
				.toList();
	}

	@Override
	public OrderDto createOrder(@NonNull OrderData orderData) {
		var totalPrice = orderData.items().stream()
				.map(itemRequest -> itemRequest.price()
						.multiply(BigDecimal.valueOf(itemRequest.quantity()))
				)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		var orderWithoutItems = orderRepository.save(Order.builder()
				.customerName(orderData.customerName())
				.customerEmail(orderData.customerEmail())
				.totalPrice(totalPrice)
				.build()
		);

		var order = orderWithoutItems.toBuilder()
				.items(orderData.items().stream()
						.map(itemData ->
								OrderItemMapper.toEntity(itemData, orderWithoutItems))
						.toList()
				)
				.status(OrderStatus.CONFIRMED)
				.build();

		return OrderMapper.toDto(orderRepository.save(order));
	}
}
