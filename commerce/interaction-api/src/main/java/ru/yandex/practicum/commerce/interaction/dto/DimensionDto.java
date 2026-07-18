package ru.yandex.practicum.commerce.interaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

/**
 * Размеры товара
 * <p></p>
 * @param width  Ширина
 * @param height Высота
 * @param depth  Глубина
 */
@Builder
public record DimensionDto(

		@NotNull
		@Positive
		Float width,

		@NotNull
		@Positive
		Float height,

		@NotNull
		@Positive
		Float depth
) {
}
