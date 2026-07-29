package ru.yandex.practicum.inventory.dto;

import lombok.Builder;

/**
 * Результат резервирования товара
 * @param success Резервирование выполнено успешно
 * @param availableQuantity Доступное количество после операции
 * @param message Сообщение
 */
@Builder
public record ReserveResponse(

        boolean success,

        Integer availableQuantity,

        String message
) {
}
