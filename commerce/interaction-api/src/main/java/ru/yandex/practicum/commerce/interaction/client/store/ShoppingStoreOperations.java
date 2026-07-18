package ru.yandex.practicum.commerce.interaction.client.store;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.enums.QuantityState;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;

import java.util.UUID;

@SuppressWarnings("unused")
public interface ShoppingStoreOperations {

	/**
	 * Получение страницы товаров указанной категории
	 * <p></p>
	 *
	 * @param category Категория товаров: Управление, Датчики и т.д.
	 *                 </br>{@code pageNumber} - {@code Default value : 0} Индекс страниц (0..N)
	 *                 </br>{@code pageSize} - {@code Default value : 20} Размер страницы, которая будет возвращена
	 *                 </br>{@code sort} - Критерии сортировки в формате: {@code property,(asc|desc)}.
	 *                 </br>По умолчанию порядок сортировки по возрастанию.
	 *                 Поддерживаются несколько критериев сортировки.
	 * @return {@link PageProductDto} - {@code 200 OK} Возвращает постраничный список товаров указанной категории.
	 * <p>
	 * В ответе содержатся бизнес-данные товаров ({@link ProductDto}). Фактически эндпоинт возвращает объект Page
	 * из Spring Data, который дополнительно содержит метаданные пагинации.
	 */
	@GetMapping
	PageProductDto getSoppingPageByCategory(
			@RequestParam @NotBlank String category,
			Pageable pageable
	);

	/**
	 * Создание нового товара в ассортименте
	 * <p></p>
	 *
	 * @param productDto Описательная часть вновь добавляемого товара в
	 *                   систему, например нового роутера и т.д.
	 * @return {@link ProductDto} - {@code 200 OK} Товар создан с присвоением соответствующего ID
	 */
	@PutMapping
	ProductDto createNewProduct(@RequestBody @NotNull @Valid ProductDto productDto);

	/**
	 * Обновление товара в ассортименте, например уточнение описания, характеристик и т.д.
	 * <p></p>
	 *
	 * @param productDto Описательная часть изменяемого товара в системе
	 * @return {@link ProductDto} - {@code 200 OK} Товар обновлен, возвращается информация из БД
	 * @throws ProductNotFoundException {@code 404 NOT_FOUND} Ошибка, товар по идентификатору в БД не найден
	 */
	@PostMapping
	ProductDto updateProduct(@RequestBody @NotNull @Valid ProductDto productDto)
			throws ProductNotFoundException;

	/**
	 * Удалить товар из ассортимента магазина. Функция для менеджерского состава.
	 * <p></p>
	 *
	 * @param productId Идентификатор товара в БД на удаление из ассортимента.
	 * @return {@code  boolean} - {@code 200 OK} Признак успеха операции. true - если успешно,
	 * false - во всех остальных случаях
	 * @throws ProductNotFoundException {@code 404 NOT_FOUND} Ошибка, товар по идентификатору в БД не найден
	 */
	@PostMapping("/removeProductFromStore")
	boolean removeProductFromStore(@RequestBody @NotNull UUID productId)
			throws ProductNotFoundException;

	/**
	 * Установка статуса по товару. API вызывается со стороны склада.
	 * <p></p>
	 * <p>
	 * {@code request} - Запрос на изменение статуса товара в магазине,
	 * например: "Закончился", "Мало" и т.д.
	 *
	 * @return {@code boolean} - {@code 200 OK} Статус успешно обновлен
	 * @throws ProductNotFoundException {@code 404 NOT_FOUND} Ошибка, товар по идентификатору в БД не найден
	 */
	@PostMapping("/quantityState")
	boolean setProductQuantityState(@RequestParam @NotNull UUID productId,
	                                @RequestParam @NotNull QuantityState quantityState)
			throws ProductNotFoundException;

	/**
	 * Получить сведения по товару из БД.
	 * <p></p>
	 *
	 * @param productId Идентификатор товара в БД.
	 * @return {@link ProductDto} - {@code 200 OK} Актуальный товар со всеми сведениями из БД
	 * @throws ProductNotFoundException {@code 404 NOT_FOUND} Ошибка, товар по идентификатору в БД не найден
	 */
	@GetMapping("/{productId}")
	ProductDto getProductById(@PathVariable @NotNull UUID productId)
			throws ProductNotFoundException;
}
