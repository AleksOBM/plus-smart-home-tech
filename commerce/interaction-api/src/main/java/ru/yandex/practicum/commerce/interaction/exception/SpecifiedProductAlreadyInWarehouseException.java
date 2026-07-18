package ru.yandex.practicum.commerce.interaction.exception;

import org.springframework.http.HttpStatus;

public class SpecifiedProductAlreadyInWarehouseException extends CustomException {
	public SpecifiedProductAlreadyInWarehouseException(String productId) {
		super(HttpStatus.ALREADY_REPORTED, "Товар с id " +  productId + " уже есть на складе");
	}
}
