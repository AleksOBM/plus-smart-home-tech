package ru.yandex.practicum.order.exception;

public class InventoryServiceUnavailableException extends ServiceUnavailableException {

	public InventoryServiceUnavailableException(Long productId, Throwable cause) {
		super("""
						Сервис склад временно недоступен.
						Не удалось выполнить операцию для товара с id: %s.
						Повторите попытку позже.
						""".formatted(productId),
				cause
		);
	}
}
