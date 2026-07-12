package ru.yandex.practicum.commerce.cart.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.cart.fasade.ShoppingCartFasade;
import ru.yandex.practicum.commerce.interaction.annotations.Loggable;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController implements ShoppingCartOperations {

	private final ShoppingCartFasade shoppingCartFasade;

	/// Получить актуальную корзину для авторизованного пользователя.
	@Loggable
	@Override
	public ShoppingCartDto getShoppingCart(String userId)
			throws NotAuthorizedUserException {
		return null;
	}

	@Override
	public ShoppingCartDto addProductToShoppingCart(
			String userId,
			@NotNull
			@NotEmpty
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
