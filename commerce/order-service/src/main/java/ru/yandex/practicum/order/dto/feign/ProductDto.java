package ru.yandex.practicum.order.dto.feign;

import java.math.BigDecimal;

/**
 * Информация о товаре на момент заказа
 * @param id ID товара
 * @param name Название товара
 * @param description Описание товара
 * @param price Цена в рублях
 * @param active Товар активен и доступен для продажи
 */
public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean active
) {
}