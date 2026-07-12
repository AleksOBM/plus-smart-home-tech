package ru.yandex.practicum.commerce.cart.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.commerce.cart.model.ShoppingCart;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;

import java.util.UUID;

@UtilityClass
public class ShoppingCartMapper {

	public ShoppingCartDto toDto(@NonNull ShoppingCart cart) {
		return ShoppingCartDto.builder()
				.shoppingCartId(cart.getShoppingCartId())
				.products(cart.getProducts())
				.build();
	}

	public ShoppingCart toEntity(@NonNull ShoppingCartDto dto) {
		return ShoppingCart.builder()
				.shoppingCartId(dto.shoppingCartId() == null ? UUID.randomUUID() : dto.shoppingCartId())
				.products(dto.products())
				.build();
	}
}
