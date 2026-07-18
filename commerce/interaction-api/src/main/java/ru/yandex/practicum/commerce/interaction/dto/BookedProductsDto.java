package ru.yandex.practicum.commerce.interaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

/// Общие сведения о зарезервированных товарах по корзине
///
/// @param deliveryWeight Общий вес доставки
/// @param deliveryVolume Общие объём доставки
/// @param fragile        Есть ли хрупкие вещи в доставке
@Builder
public record BookedProductsDto(

		@NotNull
		@Positive
		Float deliveryWeight,

		@NotNull
		@Positive
		Float deliveryVolume,

		@NotNull
		Boolean fragile
) {
}
