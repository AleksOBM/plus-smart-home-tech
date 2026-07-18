package ru.yandex.practicum.commerce.interaction.exception;

import org.springframework.http.HttpStatus;

public class NotAuthorizedUserException extends CustomException {
	public NotAuthorizedUserException(String username) {
		super(HttpStatus.UNAUTHORIZED, "Пользователь " + username + " не авторизован");
	}
}
