package ru.yandex.practicum.commerce.interaction.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

/// Запрос на увеличение единиц товара по его идентификатору
///
/// @param productId Идентификатор товара в БД
/// @param quantity  Количество единиц товара для добавления на склад
public record AddProductToWarehouseRequest(

		@NotNull
		UUID productId,

		@NotNull
		@Positive
		Integer quantity
) {
}
