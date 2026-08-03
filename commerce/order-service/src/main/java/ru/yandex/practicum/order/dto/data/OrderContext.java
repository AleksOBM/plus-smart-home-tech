package ru.yandex.practicum.order.dto.data;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record OrderContext(
		Long productId,
		String customerName,
		String customerEmail,
		String productName,
		Integer quantity,
		BigDecimal price
) {
}
