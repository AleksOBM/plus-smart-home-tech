package ru.yandex.practicum.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Позиция заказа со снимком данных товара
 *
 * @param productId ID товара
 * @param quantity  Количество
 */
public record OrderItemRequest(

		@NotNull(message = "ID товара обязателен")
		Long productId,

		@NotNull(message = "Количество обязательно")
		@Min(value = 1, message = "Количество должно быть не менее 1")
		Integer quantity
) {
}
