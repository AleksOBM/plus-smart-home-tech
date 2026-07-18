package ru.yandex.practicum.commerce.cart.service;

import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.requests.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface DatabaseCartService {

	ShoppingCartDto addProductToShoppingCart(String username,
	                                         Map<UUID, Integer> productsInStore
	);

	ShoppingCartDto getShoppingCart(String username);

	ShoppingCartDto changeProductQuantity(String username,
	                                      ChangeProductQuantityRequest request
	);

	ShoppingCartDto removeProductsFromShoppingCard(String username, Set<UUID> productIds);

	void deactivateShoppingCart(String username);
}
