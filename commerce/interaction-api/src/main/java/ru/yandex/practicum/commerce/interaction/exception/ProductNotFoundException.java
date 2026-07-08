package ru.yandex.practicum.commerce.interaction.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException {

	@Getter
	private final HttpStatus httpStatus;

	@Getter
	private final String userMessage;

	public ProductNotFoundException(String message, HttpStatus httpStatus) {
		super(message);
		userMessage = message;
		this.httpStatus = httpStatus;
	}
}
