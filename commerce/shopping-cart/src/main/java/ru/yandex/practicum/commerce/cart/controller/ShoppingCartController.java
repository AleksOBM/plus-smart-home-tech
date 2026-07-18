package ru.yandex.practicum.commerce.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.cart.fasade.ShoppingCartFasade;
import ru.yandex.practicum.commerce.interaction.annotations.Loggable;
import ru.yandex.practicum.commerce.interaction.annotations.LoggingAspect;
import ru.yandex.practicum.commerce.interaction.client.cart.ShoppingCartOperations;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.interaction.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.interaction.requests.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@Import(LoggingAspect.class)
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController implements ShoppingCartOperations {

	private final ShoppingCartFasade shoppingCartFasade;

	@Loggable
	@Override
	public ShoppingCartDto getShoppingCart(String username)
			throws NotAuthorizedUserException {
		checkUser(username);
		return shoppingCartFasade.getShoppingCart(username);
	}

	@Override
	public ShoppingCartDto addProductToShoppingCart(
			String username,
			Map<UUID, Integer> productsInStore
	) {
		checkUser(username);
		return shoppingCartFasade.addProductToShoppingCart(username, productsInStore);
	}

	@Override
	public ShoppingCartDto changeProductQuantity(String username,
	                                             ChangeProductQuantityRequest request
	) throws NotAuthorizedUserException, NoProductsInShoppingCartException {
		checkUser(username);
		return shoppingCartFasade.changeProductQuantity(username, request);
	}

	@Override
	public ShoppingCartDto removeProductsFromShoppingCard(String username,
	                                                      Set<UUID> productIds
	) throws NotAuthorizedUserException, NoProductsInShoppingCartException {
		checkUser(username);
		return shoppingCartFasade.removeProductsFromShoppingCard(username, productIds);
	}

	@Override
	public void deactivateShoppingCart(String username)
			throws NotAuthorizedUserException {
		checkUser(username);
		shoppingCartFasade.deactivateShoppingCart(username);
	}

	private void checkUser(@NonNull String username)
			throws NotAuthorizedUserException {
		if (username.isBlank()) {
			throw new NotAuthorizedUserException(username);
		}
	}

}
