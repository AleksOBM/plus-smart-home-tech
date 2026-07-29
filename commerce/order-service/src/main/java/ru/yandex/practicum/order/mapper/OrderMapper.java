package ru.yandex.practicum.order.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.entity.Order;

@UtilityClass
public class OrderMapper {

	public OrderDto toDto(@NonNull Order order) {
		return OrderDto.builder()
				.id(order.getId())
				.customerName(order.getCustomerName())
				.customerEmail(order.getCustomerEmail())
				.status(order.getStatus().name())
				.totalPrice(order.getTotalPrice())
				.statusDetails(order.getStatusDetails())
				.createdAt(order.getCreatedAt())
				.items(order.getItems().stream().map(OrderItemMapper::toDto).toList())
				.build();
	}
}
