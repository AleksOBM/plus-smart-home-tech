package ru.yandex.practicum.order.exception;

public abstract class ServiceUnavailableException extends RuntimeException {
	public ServiceUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}
}