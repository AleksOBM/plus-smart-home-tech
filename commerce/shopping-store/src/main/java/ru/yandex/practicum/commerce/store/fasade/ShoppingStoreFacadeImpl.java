package ru.yandex.practicum.commerce.store.fasade;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.store.service.DatabaseStoreService;
import ru.yandex.practicum.commerce.store.service.InteractionStoreService;

@Service
@RequiredArgsConstructor
public class ShoppingStoreFacadeImpl implements ShoppingStoreFasade {

	@Delegate
	private final DatabaseStoreService databaseStoreService;

	@Delegate
	private final InteractionStoreService interactionStoreService;

}
