package ru.yandex.practicum.commerce.interaction.exception;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.commerce.interaction.error.CustomException;

public class SpecifiedProductAlreadyInWarehouseException extends CustomException {
	public SpecifiedProductAlreadyInWarehouseException(String userMessage) {
		super(HttpStatus.ALREADY_REPORTED, userMessage);
	}
}
