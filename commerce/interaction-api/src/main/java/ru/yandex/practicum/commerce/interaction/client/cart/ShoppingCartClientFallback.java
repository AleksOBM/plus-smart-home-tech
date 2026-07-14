package ru.yandex.practicum.commerce.interaction.client.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.interaction.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.interaction.requests.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class ShoppingCartClientFallback implements
		ShoppingCartOperations, ShoppingCartClient {

	@Override
	public ShoppingCartDto getShoppingCart(String username)
			throws NotAuthorizedUserException {
		log.info("ShoppingCartFallback.getShoppingCart()");
		return null;
	}

	@Override
	public ShoppingCartDto addProductToShoppingCart(
			String userId,
			@NotNull
			@Positive
			Map<@NotNull UUID, @NotNull @Positive Long> productsInStore
	) {
		log.info("ShoppingCartFallback.addProductToShoppingCart()");
		return null;
	}

	@Override
	public ShoppingCartDto changeProductQuantity(String userId,
	                                             ChangeProductQuantityRequest request
	) throws NotAuthorizedUserException, NoProductsInShoppingCartException {
		log.info("ShoppingCartFallback.changeProductQuantity()");
		return null;
	}

	@Override
	public ShoppingCartDto removeProductsFromShoppingCard(String userId,
	                                                      Set<@NotNull UUID> productIds
	) throws NotAuthorizedUserException, NoProductsInShoppingCartException {
		log.info("ShoppingCartFallback.removeProductsFromShoppingCart()");
		return null;
	}

	@Override
	public void deactivateShoppingCart(String userId)
			throws NotAuthorizedUserException {
		log.info("ShoppingCartFallback.deactivateShoppingCart()");
	}
}
