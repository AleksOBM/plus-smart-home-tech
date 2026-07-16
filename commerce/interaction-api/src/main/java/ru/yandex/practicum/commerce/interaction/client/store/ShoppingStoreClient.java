package ru.yandex.practicum.commerce.interaction.client.store;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store",
		fallback = ShoppingStoreClientFallback.class,
		fallbackFactory = ShoppingStoreClientFallbackFactory.class
)
public interface ShoppingStoreClient extends ShoppingStoreOperations {
}
