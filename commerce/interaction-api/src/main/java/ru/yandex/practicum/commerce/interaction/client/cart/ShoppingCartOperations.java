package ru.yandex.practicum.commerce.interaction.client.cart;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.interaction.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.interaction.requests.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ShoppingCartOperations {

	@GetMapping
	ShoppingCartDto getShoppingCart(@RequestParam String userId)
			throws NotAuthorizedUserException;

	@PutMapping
	ShoppingCartDto addProductToShoppingCart(@RequestParam String userId,
	                                         @RequestBody
	                                         @NotNull
	                                         @NotEmpty
	                                         Map<@NotNull UUID, @NotNull @Positive Long> productsInStore
	);

	@PostMapping("/change-quantity")
	ShoppingCartDto changeProductQuantity(@RequestParam String userId,
	                                      @RequestBody ChangeProductQuantityRequest request)
			throws NotAuthorizedUserException, NoProductsInShoppingCartException;

	@PostMapping("/remove")
	ShoppingCartDto removeProductsFromShoppingCard(@RequestParam String userId,
	                                               @RequestBody Set<@NotNull UUID> productIds)
			throws NotAuthorizedUserException, NoProductsInShoppingCartException;

	@DeleteMapping
	void deactivateShoppingCart(@RequestParam String userId)
			throws NotAuthorizedUserException;
}
