package ru.yandex.practicum.commerce.interaction.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/// Запрос на увеличение единиц товара по его идентификатору
///
/// @param productId Идентификатор товара в БД
/// @param quantity  Количество единиц товара для добавления на склад
public record AddProductToWarehouseRequest(

		UUID productId,

		@NotNull
		@Size(min = 1)
		Integer quantity
) {
}
