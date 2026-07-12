package ru.yandex.practicum.commerce.cart.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

public interface DatabaseCartService {

	ShoppingCartDto addProductToShoppingCart(String userId,
	                                         @NotNull
	                                         @NotEmpty
	                                         Map<@NotNull UUID, @NotNull @Positive Long> productsInStore
	);
}
