package ru.yandex.practicum.order.exception;

public class ProductServiceUnavailableException extends ServiceUnavailableException {

	public ProductServiceUnavailableException(Long productId, Throwable cause) {
		super("""
						Сервис товаров временно недоступен.
						Не удалось получить данные для товара с id: %s.
						Повторите попытку позже.
						""".formatted(productId),
				cause
		);
	}
}
