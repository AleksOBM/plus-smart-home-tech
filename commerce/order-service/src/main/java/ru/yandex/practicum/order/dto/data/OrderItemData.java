package ru.yandex.practicum.order.dto.data;

import lombok.Builder;

import java.math.BigDecimal;

/**
 *
 * @param productId
 * @param productName
 * @param quantity
 * @param price
 */
@Builder
public record OrderItemData(
		Long productId,
		String productName,
		Integer quantity,
		BigDecimal price
) {
}
