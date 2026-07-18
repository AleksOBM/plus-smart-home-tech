package ru.yandex.practicum.commerce.interaction.client.warehouse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.commerce.interaction.dto.AddressDto;
import ru.yandex.practicum.commerce.interaction.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.interaction.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.interaction.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.interaction.requests.AddProductToWarehouseRequest;
import ru.yandex.practicum.commerce.interaction.requests.NewProductInWarehouseRequest;

@SuppressWarnings("unused")
public interface WarehouseOperations {

	/**
	 * Добавить новый товар на склад.
	 * <p></p>
	 *
	 * @param request Описание нового товара для обработки складом.
	 * @throws SpecifiedProductAlreadyInWarehouseException Ошибка, товар с таким описанием уже зарегистрирован на складе
	 */
	@PutMapping
	void createNewProduct(@RequestBody NewProductInWarehouseRequest request)
			throws SpecifiedProductAlreadyInWarehouseException;

	/**
	 * Предварительно проверить что количество товаров на
	 * складе достаточно для данной корзиный продуктов.
	 * <p></p>
	 *
	 * @param cartDto Корзина товаров.
	 * @return {@link BookedProductsDto} - Общие сведения по бронированию
	 * @throws ProductInShoppingCartLowQuantityInWarehouse Ошибка, товар из корзины не находится в требуемом количестве на складе
	 */
	@PostMapping("/check")
	BookedProductsDto checkProductQuantity(@RequestBody ShoppingCartDto cartDto)
			throws ProductInShoppingCartLowQuantityInWarehouse;

	/**
	 * Принять товар на склад.
	 * <p></p>
	 *
	 * @param request Запрос на добавление определенного
	 *                количества определенного товара.
	 * @throws NoSpecifiedProductInWarehouseException Нет информации о товаре на складе
	 */
	@PostMapping("/add")
	void addProductQuantity(@RequestBody AddProductToWarehouseRequest request)
			throws NoSpecifiedProductInWarehouseException;

	/**
	 * Предоставить адрес склада для расчёта доставки.
	 * <p></p>
	 *
	 * @return {@link AddressDto} - Актуальный адрес склада
	 */
	@GetMapping("/address")
	AddressDto getWarehouseAddress();
}
