package ru.yandex.practicum.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Запрос на создание категории
 * @param name Название категории
 * @param description Описание категории
 */
public record CreateCategoryRequest(

        @NotBlank(message = "Название категории обязательно")
        @Size(max = 255, message = "Название категории не может быть длиннее 255 символов")
        String name,

        @Size(max = 500, message = "Описание не может быть длиннее 500 символов")
        String description
) {
}