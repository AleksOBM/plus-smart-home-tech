package ru.yandex.practicum.order.exception;

import feign.FeignException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ExceptionMapper {

	public Optional<OrderProcessingException> mapProductException(@NonNull FeignException exception) {
		if (exception.status() == 404) {
			return Optional.of(new OrderProcessingException(exception.getMessage()));
		}

		return Optional.empty();
	}

	public Optional<OrderProcessingException> mapInventoryException(@NonNull FeignException exception) {
		if (exception.status() == 404) {
			return Optional.of(new OrderProcessingException(exception.getMessage()));
		}

		if (exception.status() == 409) {
			return Optional.of(new OrderProcessingException(exception.getMessage()));
		}

		return Optional.empty();
	}
}
