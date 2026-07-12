package ru.yandex.practicum.commerce.cart.fasade;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.cart.service.DatabaseCartService;
import ru.yandex.practicum.commerce.cart.service.InteractionCartService;

@Service
@RequiredArgsConstructor
public class ShoppingCartFacadeImpl implements ShoppingCartFasade {

	@Delegate
	private final DatabaseCartService databaseService;

	@Delegate
	private final InteractionCartService interactionService;

}