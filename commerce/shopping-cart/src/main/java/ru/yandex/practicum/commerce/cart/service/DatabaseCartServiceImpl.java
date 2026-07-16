package ru.yandex.practicum.commerce.cart.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.cart.repository.CartRepository;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NotAuthorizedUserException;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DatabaseCartServiceImpl implements DatabaseCartService {

	private final CartRepository cartRepository;

	@Override
	public ShoppingCartDto addProductToShoppingCart(String userId,
	                                                @NotNull
	                                                @NotEmpty
	                                                Map<@NotNull UUID, @NotNull @Positive Long> productsInStore
	) throws NotAuthorizedUserException {
		return null;
	}

}
