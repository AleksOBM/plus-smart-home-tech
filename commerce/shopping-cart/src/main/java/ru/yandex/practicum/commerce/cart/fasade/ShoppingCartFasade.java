package ru.yandex.practicum.commerce.cart.fasade;

import ru.yandex.practicum.commerce.cart.service.DatabaseCartService;
import ru.yandex.practicum.commerce.cart.service.InteractionCartService;

public interface ShoppingCartFasade extends
		DatabaseCartService,
		InteractionCartService {
}
