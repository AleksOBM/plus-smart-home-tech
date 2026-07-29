package ru.yandex.practicum.order.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Информация о заказе
 * @param id ID заказа
 * @param customerName Имя покупателя
 * @param customerEmail Email покупателя
 * @param status Статус заказа
 * @param totalPrice Общая стоимость заказа
 * @param statusDetails Дополнительная информация о статусе
 * @param createdAt Дата создания заказа
 * @param items Позиции заказа
 */
@Builder
public record OrderDto(

        Long id,

        String customerName,

        String customerEmail,

        String status,

        BigDecimal totalPrice,

        String statusDetails,

        LocalDateTime createdAt,

        List<OrderItemDto> items
) {
}
