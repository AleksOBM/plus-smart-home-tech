package ru.yandex.practicum.commerce.interaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/// Общие сведения о зарезервированных товарах по корзине
///
/// @param deliveryWeight Общий вес доставки
/// @param deliveryVolume Общие объём доставки
/// @param fragile        Есть ли хрупкие вещи в доставке
public record BookedProductsDto(

		@NotNull
		@Positive
		Double deliveryWeight,

		@NotNull
		@Positive
		Double deliveryVolume,

		@NotNull
		Boolean fragile
) {
}
