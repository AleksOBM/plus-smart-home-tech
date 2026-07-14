package ru.yandex.practicum.commerce.interaction.exception;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.commerce.interaction.error.CustomException;


public class ProductInShoppingCartLowQuantityInWarehouse extends CustomException {

	public ProductInShoppingCartLowQuantityInWarehouse(String userMessage) {
		super(HttpStatus.UNPROCESSABLE_ENTITY, userMessage);
	}
}
