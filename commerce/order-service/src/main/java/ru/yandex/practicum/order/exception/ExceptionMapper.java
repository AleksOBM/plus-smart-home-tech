package ru.yandex.practicum.order.exception;

import feign.FeignException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ExceptionMapper {

	public RuntimeException mapInventoryException(@NonNull FeignException ex, long productId) {
		return switch (ex.status()) {
			case 404, 409 -> new OrderProcessingException(ex.getMessage());
			default -> new InventoryServiceUnavailableException(productId, ex);
		};
	}

	public RuntimeException mapProductException(@NonNull FeignException ex, long productId) {
		if (ex.status() == 404) {
			return new OrderProcessingException(ex.getMessage());
		}
		return new ProductServiceUnavailableException(productId, ex);
	}
}
