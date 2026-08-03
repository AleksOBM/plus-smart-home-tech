package ru.yandex.practicum.order.fallback;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.order.dto.feign.ReserveRequest;
import ru.yandex.practicum.order.dto.feign.ReserveResponse;
import ru.yandex.practicum.order.exception.ExceptionMapper;
import ru.yandex.practicum.order.exception.InventoryServiceUnavailableException;
import ru.yandex.practicum.order.feign.InventoryClient;

@Slf4j
@Component
public class InventoryClientFallbackFactory implements FallbackFactory<InventoryClient> {

	@Override
	public InventoryClient create(Throwable cause) {
		return new InventoryClient() {

			@Override
			public ReserveResponse reserveProduct(ReserveRequest request) {
				throw recognizeException(cause, request.productId());
			}

			@Override
			public ReserveResponse releaseProduct(ReserveRequest request) {
				throw recognizeException(cause, request.productId());
			}
		};
	}

	private static RuntimeException recognizeException(Throwable cause, long productId) {
		if (cause instanceof FeignException ex) {
			return ExceptionMapper.mapInventoryException(ex, productId);
		}

		return new InventoryServiceUnavailableException(productId, cause);
	}
}
