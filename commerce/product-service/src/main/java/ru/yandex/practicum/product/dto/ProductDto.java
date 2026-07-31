package ru.yandex.practicum.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Информация о товаре
 *
 * @param id          ID товара
 * @param name        Название товара
 * @param description Описание товара
 * @param price       Цена в рублях
 * @param category    Категория товаров
 * @param imageUrl    URL изображения товара
 * @param active      Товар активен и доступен для продажи
 */
@Builder
public record ProductDto(
		Long id,
		String name,
		String description,
		BigDecimal price,
		CategoryDto category,
		String imageUrl,
		Boolean active
) {
}