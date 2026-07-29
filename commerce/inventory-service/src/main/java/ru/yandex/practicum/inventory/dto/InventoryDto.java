package ru.yandex.practicum.inventory.dto;

import lombok.Builder;

/**
 * Информация об остатках товара на складе
 * @param id ID записи
 * @param productId ID товара
 * @param quantity Общее количество на складе
 * @param reservedQuantity Зарезервированное количество
 * @param availableQuantity Доступное количество (общее - зарезервированное)
 */
@Builder
public record InventoryDto(

        Long id,

        Long productId,

        Integer quantity,

        Integer reservedQuantity,

        Integer availableQuantity
) {
}
