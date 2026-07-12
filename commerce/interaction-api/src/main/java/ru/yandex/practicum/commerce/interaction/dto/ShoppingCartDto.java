package ru.yandex.practicum.commerce.interaction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

/**
 * Корзина товаров в онлайн магазине
 * <p></p>
 * @param shoppingCartId Идентификатор корзины в БД
 * @param products       Отображение идентификатора товара на отобранное количество
 */
@Builder
public record ShoppingCartDto(

		@NotNull
		UUID shoppingCartId,

		@NotNull
		Map<UUID, Integer> products
) {
}
