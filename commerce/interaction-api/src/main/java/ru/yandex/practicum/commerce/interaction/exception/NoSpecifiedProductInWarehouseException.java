package ru.yandex.practicum.commerce.interaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class NoSpecifiedProductInWarehouseException extends RuntimeException {

	private final HttpStatus httpStatus;
	private final String userMessage;
}
