package ru.yandex.practicum.commerce.interaction.client.store;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ShoppingStoreClientFallbackFactory implements
		FallbackFactory<ShoppingStoreClient> {

	@Override
	public ShoppingStoreClient create(Throwable cause) {
		if (cause instanceof RuntimeException) {
			return new ShoppingStoreClientFallback();
		}
		return null;
	}
}
