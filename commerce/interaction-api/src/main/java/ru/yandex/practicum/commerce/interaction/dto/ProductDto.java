package ru.yandex.practicum.commerce.interaction.dto;

import jakarta.validation.constraints.NotNull;
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
public record ProductDto(

		UUID productId,

		@NotNull
		String productName,

		@NotNull
		String description,

		String imageSrc,

		@NotNull
		QuantityState quantityState,

		@NotNull
		ProductState productState,

		ProductCategory productCategory,

		@NotNull
		Float price
) {
}
