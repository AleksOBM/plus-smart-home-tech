package ru.yandex.practicum.commerce.interaction.requests;

import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.commerce.interaction.enums.QuantityState;

import java.util.UUID;

/// Запрос на изменение статуса остатка товара
///
/// @param productId     Идентификатор товара
/// @param quantityState Статус, перечисляющий состояние остатка как свойства товара
public record SetProductQuantityStateRequest(

		@NotNull
		UUID productId,

		@NotNull
		QuantityState quantityState
) {
}
