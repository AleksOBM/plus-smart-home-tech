package ru.yandex.practicum.order.dto.feign;

public record ReserveRequest(
        Long productId,
        Integer quantity
) {
}