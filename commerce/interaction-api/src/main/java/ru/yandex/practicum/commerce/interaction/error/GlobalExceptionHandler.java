package ru.yandex.practicum.commerce.interaction.error;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(@NonNull CustomException ex) {
		return ResponseEntity.status(ex.getHttpStatus()).body(ex);
	}

}
