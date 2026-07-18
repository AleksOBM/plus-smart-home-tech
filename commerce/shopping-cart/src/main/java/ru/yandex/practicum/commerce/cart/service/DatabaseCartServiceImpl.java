package ru.yandex.practicum.commerce.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.cart.mapper.ShoppingCartMapper;
import ru.yandex.practicum.commerce.cart.model.ShoppingCart;
import ru.yandex.practicum.commerce.cart.repository.CartRepository;
import ru.yandex.practicum.commerce.interaction.client.warehouse.WarehouseClient;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.interaction.requests.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DatabaseCartServiceImpl implements DatabaseCartService {

	private final CartRepository cartRepository;
	private final WarehouseClient warehouseClient;

	@Override
	public ShoppingCartDto getShoppingCart(String username) {
		return ShoppingCartMapper.toDto(getShoppungCartByUserName(username));
	}

	@Override
	public ShoppingCartDto addProductToShoppingCart(String username,
	                                                Map<UUID, Integer> productsInStore
	) throws NotAuthorizedUserException {
		ShoppingCart cart = getShoppungCartByUserName(username);
		cart.getProducts().clear();
		cart.getProducts().putAll(productsInStore);
		warehouseClient.checkProductQuantity(ShoppingCartMapper.toDto(cart));

		return ShoppingCartMapper.toDto(cartRepository.save(cart));
	}

	@Override
	public ShoppingCartDto changeProductQuantity(String username,
	                                             @NonNull ChangeProductQuantityRequest request
	) {
		ShoppingCart cart = getShoppungCartByUserName(username);

		cart.getProducts().put(
				request.productId(),
				request.newQuantity()
		);

		warehouseClient.checkProductQuantity(ShoppingCartMapper.toDto(cart));

		return ShoppingCartMapper.toDto(cartRepository.save(cart));
	}

	@Override
	public ShoppingCartDto removeProductsFromShoppingCard(String username,
	                                                      @NonNull Set<UUID> productIds
	) {
		ShoppingCart cart = getShoppungCartByUserName(username);
		productIds.forEach(cart.getProducts()::remove);
		return ShoppingCartMapper.toDto(cartRepository.save(cart));
	}

	@Override
	public void deactivateShoppingCart(String username) {
		cartRepository.findByUsernameAndOpenIsTrue(username)
				.ifPresent(cart -> {
					cart.setOpen(false);
					cartRepository.save(cart);
				});
	}

	private ShoppingCart getShoppungCartByUserName(String username) {
		return cartRepository.findByUsernameAndOpenIsTrue(username)
				.orElseGet(() -> {
					try {
						return cartRepository.save(
								ShoppingCart.builder()
										.username(username)
										.build()
						);
					} catch (DataIntegrityViolationException e) {
						return cartRepository.findByUsernameAndOpenIsTrue(username)
								.orElseThrow();
					}
				});
	}

}
