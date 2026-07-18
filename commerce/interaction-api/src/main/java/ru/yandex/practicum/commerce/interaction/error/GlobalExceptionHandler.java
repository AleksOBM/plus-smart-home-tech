package ru.yandex.practicum.commerce.interaction.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.commerce.interaction.exception.CustomException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<?> handleSomeError(final @NonNull Exception ex) {
		return ResponseEntity
				.status(INTERNAL_SERVER_ERROR)
				.body(new ApiError(
								INTERNAL_SERVER_ERROR,
								ex.getClass().getTypeName(),
								ex.getMessage(),
								getStackTrace(ex),
								LocalDateTime.now()
						)
				);
	}

	@SuppressWarnings("unused")
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(final @NonNull CustomException ex) {
		return ResponseEntity
				.status(ex.getHttpStatus())
				.body(new ApiError(
								ex.getHttpStatus(),
								ex.getClass().getTypeName(),
								ex.getMessage(),
								getStackTrace(ex),
								LocalDateTime.now()
						)
				);
	}

	private String getStackTrace(@NonNull Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
