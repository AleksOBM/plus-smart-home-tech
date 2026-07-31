package ru.yandex.practicum.order.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.order.dto.data.OrderItemData;
import ru.yandex.practicum.order.dto.OrderItemDto;
import ru.yandex.practicum.order.entity.Order;
import ru.yandex.practicum.order.entity.OrderItem;

@UtilityClass
public class OrderItemMapper {

	public OrderItemDto toDto(@NonNull OrderItem orderItem) {
		return OrderItemDto.builder()
				.id(orderItem.getId())
				.productId(orderItem.getProductId())
				.productName(orderItem.getProductName())
				.quantity(orderItem.getQuantity())
				.price(orderItem.getPrice())
				.build();
	}

	public OrderItem toEntity(@NonNull OrderItemData request, Order order) {
		return OrderItem.builder()
				.order(order)
				.productId(request.productId())
				.productName(request.productName())
				.quantity(request.quantity())
				.price(request.price())
				.build();
	}
}
