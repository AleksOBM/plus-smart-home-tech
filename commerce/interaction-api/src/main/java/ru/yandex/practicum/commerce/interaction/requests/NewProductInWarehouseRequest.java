package ru.yandex.practicum.commerce.interaction.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.yandex.practicum.commerce.interaction.dto.DimensionDto;

import java.util.UUID;

/// Запрос на добавление нового товара на склад
///
/// @param productId Идентификатор товара в БД
/// @param fragile   Признак хрупкости
/// @param dimension Размеры товара
/// @param weight    Вес товара
public record NewProductInWarehouseRequest(

		@NotNull
		UUID productId,

		Boolean fragile,

		@NotNull
		DimensionDto dimension,

		@NotNull
		@Positive
		Double weight
) {
}
