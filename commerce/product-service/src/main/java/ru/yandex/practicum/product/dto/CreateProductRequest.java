package ru.yandex.practicum.product.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Запрос на создание товара
 * @param name Название товара
 * @param description Описание товара
 * @param price Цена в рублях
 * @param categoryId ID категории
 * @param imageUrl URL изображения товара
 */
public record CreateProductRequest(

        @NotBlank(message = "Название товара обязательно")
        @Size(max = 255, message = "Название не может быть длиннее 255 символов")
        String name,

        @Size(max = 2000, message = "Описание не может быть длиннее 2000 символов")
        String description,

        @NotNull(message = "Цена обязательна")
        @DecimalMin(value = "0.01", message = "Цена должна быть больше нуля")
        BigDecimal price,

        Long categoryId,

        String imageUrl
) {
}