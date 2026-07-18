package ru.yandex.practicum.commerce.interaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.UUID;

/// Ошибка, товар из корзины не находится в требуемом количестве на складе
public class ProductInShoppingCartLowQuantityInWarehouse extends CustomException {

	public ProductInShoppingCartLowQuantityInWarehouse(@NonNull Map<UUID, Integer> missingProducts) {
		super(HttpStatus.UNPROCESSABLE_ENTITY, "Недостающие товары:\n" + missingProducts);
	}
}
