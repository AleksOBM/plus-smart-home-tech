package ru.yandex.practicum.order.dto.data;

import lombok.Builder;
import ru.yandex.practicum.order.entity.OrderStatus;

import java.util.Collection;

@Builder
public record OrderData(
		String customerName,
		String customerEmail,
		OrderStatus status,
		String statusDetails,
		Collection<OrderItemData> items
) {
}
