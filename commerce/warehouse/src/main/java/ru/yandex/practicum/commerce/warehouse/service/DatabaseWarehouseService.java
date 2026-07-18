package ru.yandex.practicum.commerce.warehouse.service;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.interaction.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.interaction.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.interaction.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.interaction.requests.AddProductToWarehouseRequest;
import ru.yandex.practicum.commerce.interaction.requests.NewProductInWarehouseRequest;

@Transactional
public interface DatabaseWarehouseService {

	/**
	 * Добавить новый товар на склад.
	 * <p></p>
	 *
	 * @param request Описание нового товара для обработки складом.
	 * @throws SpecifiedProductAlreadyInWarehouseException Ошибка, товар с таким описанием уже зарегистрирован на складе
	 */
	void createNewProduct(NewProductInWarehouseRequest request);

	/**
	 * Предварительно проверить что количество товаров на
	 * складе достаточно для данной корзиный продуктов.
	 * <p></p>
	 *
	 * @param cartDto Корзина товаров.
	 * @return {@link BookedProductsDto} - Общие сведения по бронированию
	 * @throws ProductInShoppingCartLowQuantityInWarehouse Ошибка, товар из корзины не находится в требуемом количестве на складе
	 */
	BookedProductsDto checkProductQuantity(ShoppingCartDto cartDto);

	/**
	 * Принять товар на склад.
	 * <p></p>
	 *
	 * @param request Запрос на добавление определенного
	 *                количества определенного товара.
	 * @throws NoSpecifiedProductInWarehouseException Нет информации о товаре на складе
	 */
	void addProductQuantity(AddProductToWarehouseRequest request);
}
