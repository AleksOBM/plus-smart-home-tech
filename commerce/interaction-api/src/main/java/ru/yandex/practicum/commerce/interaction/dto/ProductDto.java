package ru.yandex.practicum.commerce.interaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import ru.yandex.practicum.commerce.interaction.enums.ProductCategory;
import ru.yandex.practicum.commerce.interaction.enums.ProductState;
import ru.yandex.practicum.commerce.interaction.enums.QuantityState;

import java.util.UUID;

/**
 * @param productId       Идентификатор товара в БД
 * @param productName     Наименование товара
 * @param description     Описание товара
 * @param imageSrc        Ссылка на картинку во внешнем хранилище или SVG
 * @param quantityState   Статус, перечисляющий состояние остатка как свойства товара
 * @param productState    Статус товара
 * @param productCategory Категория товара
 * @param price           Цена товара
 */
@Builder
public record ProductDto(

		UUID productId,

		@NotBlank
		String productName,

		@NotBlank
		String description,

		String imageSrc,

		@NotNull
		QuantityState quantityState,

		@NotNull
		ProductState productState,

		ProductCategory productCategory,

		@NotNull
		@Positive
		Float price
) {
}
