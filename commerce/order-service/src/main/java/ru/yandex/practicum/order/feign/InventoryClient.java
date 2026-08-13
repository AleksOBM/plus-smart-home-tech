package ru.yandex.practicum.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.order.fallback.InventoryClientFallbackFactory;
import ru.yandex.practicum.order.dto.feign.ReserveRequest;
import ru.yandex.practicum.order.dto.feign.ReserveResponse;

@FeignClient(
		name = "inventory-service",
		fallbackFactory = InventoryClientFallbackFactory.class
)
public interface InventoryClient {

	@PostMapping("/api/inventory/reserve")
	ReserveResponse reserveProduct(@RequestBody ReserveRequest request);

	@PostMapping("/release")
	ReserveResponse releaseProduct(@RequestBody ReserveRequest request);
}