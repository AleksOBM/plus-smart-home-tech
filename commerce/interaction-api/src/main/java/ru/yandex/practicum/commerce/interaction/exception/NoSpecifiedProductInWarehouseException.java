package ru.yandex.practicum.commerce.interaction.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;


public class NoSpecifiedProductInWarehouseException extends CustomException {

	public NoSpecifiedProductInWarehouseException(UUID productId) {
		super(HttpStatus.NOT_FOUND, "Продукт с id " + productId + " отсутствует на складе");
	}
}
