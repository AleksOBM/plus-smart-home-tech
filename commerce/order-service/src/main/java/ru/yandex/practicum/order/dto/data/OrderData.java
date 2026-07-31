package ru.yandex.practicum.order.dto.data;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderData(
		String customerName,
		String customerEmail,
		List<OrderItemData> items
) {
}
