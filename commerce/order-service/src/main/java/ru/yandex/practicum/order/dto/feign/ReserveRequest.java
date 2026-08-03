package ru.yandex.practicum.order.dto.feign;

/**
 *
 * @param productId
 * @param quantity
 */
public record ReserveRequest(
        Long productId,
        Integer quantity
) {
}