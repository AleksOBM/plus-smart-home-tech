package ru.yandex.practicum.commerce.interaction.exception;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.commerce.interaction.error.CustomException;

public class NotAuthorizedUserException extends CustomException {
	public NotAuthorizedUserException(String userMessage) {
		super(HttpStatus.UNAUTHORIZED, userMessage);
	}
}
