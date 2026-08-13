package ru.yandex.practicum.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.dto.data.OrderData;
import ru.yandex.practicum.order.entity.Order;
import ru.yandex.practicum.order.entity.OrderItem;
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

		if (orderData.status() == OrderStatus.PENDING_CONFIRMATION) {
			var existingOrder = orderRepository.findByCustomerEmailAndStatus(
					orderData.customerEmail(),
					OrderStatus.PENDING_CONFIRMATION
			);

			if (existingOrder.isPresent()) {
				return OrderMapper.toDto(existingOrder.get());
			}
		}

		BigDecimal totalPrice = orderData.items().stream()
				.map(item -> item.price()
						.multiply(BigDecimal.valueOf(item.quantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		Order order = Order.builder()
				.customerName(orderData.customerName())
				.customerEmail(orderData.customerEmail())
				.status(orderData.status())
				.statusDetails(orderData.statusDetails())
				.totalPrice(totalPrice)
				.build();

		List<OrderItem> items = orderData.items().stream()
				.map(item -> OrderItemMapper.toEntity(item, order))
				.toList();

		order.setItems(items);

		return OrderMapper.toDto(orderRepository.save(order));
	}
}
