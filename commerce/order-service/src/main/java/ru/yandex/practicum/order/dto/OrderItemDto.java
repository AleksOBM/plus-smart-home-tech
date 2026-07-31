package ru.yandex.practicum.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Позиция в заказе
 *
 * @param id          ID позиции
 * @param productId   ID товара
 * @param productName Название товара
 * @param quantity    Количество
 * @param price       Цена за единицу
 */
@Builder
public record OrderItemDto(
		Long id,
		Long productId,
		String productName,
		Integer quantity,
		BigDecimal price
) {
}