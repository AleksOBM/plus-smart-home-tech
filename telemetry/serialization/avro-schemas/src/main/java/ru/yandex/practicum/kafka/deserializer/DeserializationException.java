package ru.yandex.practicum.kafka.deserializer;

public class DeserializationException extends RuntimeException {
	public DeserializationException(String message, Exception e) {
		super(message);
	}
}
