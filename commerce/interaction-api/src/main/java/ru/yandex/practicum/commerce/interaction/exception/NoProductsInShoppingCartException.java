package ru.yandex.practicum.commerce.interaction.exception;

import org.springframework.http.HttpStatus;

public class NoProductsInShoppingCartException extends CustomException {

	public NoProductsInShoppingCartException(String userMessage) {
		super(HttpStatus.NOT_FOUND, userMessage);
	}
}
