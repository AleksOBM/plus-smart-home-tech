package ru.yandex.practicum.product.dto;

import lombok.Builder;

/**
 * Категория товаров
 * @param id ID категории
 * @param name Название категории
 * @param description Описание категории
 */
@Builder
public record CategoryDto(

        Long id,

        String name,

        String description
) {
}