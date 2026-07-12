package ru.yandex.practicum.commerce.interaction.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

/// Запрос на изменение количества единиц товара
///
/// @param productId   Идентификатор товара
/// @param newQuantity Новое количество товара
public record ChangeProductQuantityRequest(

		@NotNull
		UUID productId,

		@NotNull
		@Positive
		Integer newQuantity
) {
}
