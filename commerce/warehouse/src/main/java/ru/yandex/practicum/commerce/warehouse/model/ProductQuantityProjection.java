package ru.yandex.practicum.commerce.warehouse.model;

import java.util.UUID;

public record ProductQuantityProjection(
        UUID productId,
        Integer quantityFree
) {}