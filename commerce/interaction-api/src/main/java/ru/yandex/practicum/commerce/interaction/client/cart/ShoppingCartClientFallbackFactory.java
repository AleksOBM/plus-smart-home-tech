package ru.yandex.practicum.commerce.interaction.client.cart;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartClientFallbackFactory implements
		FallbackFactory<ShoppingCartClient> {

	@Override
	public ShoppingCartClient create(Throwable cause) {
		if (cause instanceof RuntimeException) {
			return new ShoppingCartClientFallback();
		}
		return null;
	}
}
