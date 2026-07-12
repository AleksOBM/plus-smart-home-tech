package ru.yandex.practicum.commerce.interaction.client.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.interaction.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.interaction.requests.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ShoppingCartClientFallback implements
		ShoppingCartOperations, ShoppingCartClient {

	@Override
	public ShoppingCartDto getShoppingCart(String username)
			throws NotAuthorizedUserException {
		return null;
	}

	@Override
	public ShoppingCartDto addProductToShoppingCart(
			String userId,
			@NotNull
			@Positive
			Map<@NotNull UUID, @NotNull @Positive Long> productsInStore
	) {
		return null;
	}

	@Override
	public ShoppingCartDto changeProductQuantity(String userId,
	                                             ChangeProductQuantityRequest request
	) throws NotAuthorizedUserException, NoProductsInShoppingCartException {

		return null;
	}

	@Override
	public ShoppingCartDto removeProductsFromShoppingCard(String userId,
	                                                      Set<@NotNull UUID> productIds
	) throws NotAuthorizedUserException, NoProductsInShoppingCartException {

		return null;
	}

	@Override
	public void deactivateShoppingCart(String userId)
			throws NotAuthorizedUserException {
	}
}
