package ru.yandex.practicum.commerce.store.fasade;

import ru.yandex.practicum.commerce.store.service.DatabaseStoreService;
import ru.yandex.practicum.commerce.store.service.InteractionStoreService;

public interface ShoppingStoreFasade extends
		DatabaseStoreService,
		InteractionStoreService {
}
