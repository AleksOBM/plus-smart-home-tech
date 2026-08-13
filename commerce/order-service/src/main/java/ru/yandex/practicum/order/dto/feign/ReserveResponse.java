package ru.yandex.practicum.order.dto.feign;

/**
 *
 * @param productId
 * @param reservedQuantity
 * @param availableQuantity
 */
public record ReserveResponse(
        Long productId,
        Integer reservedQuantity,
        Integer availableQuantity
) {
}