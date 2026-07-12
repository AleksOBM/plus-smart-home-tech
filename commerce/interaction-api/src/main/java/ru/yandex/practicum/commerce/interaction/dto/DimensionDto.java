package ru.yandex.practicum.commerce.interaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Размеры товара
 * <p></p>
 * @param width  Ширина
 * @param height Высота
 * @param depth  Глубина
 */
public record DimensionDto(

		@NotNull
		@Positive
		Double width,

		@NotNull
		@Positive
		Double height,

		@NotNull
		@Positive
		Double depth
) {
}
