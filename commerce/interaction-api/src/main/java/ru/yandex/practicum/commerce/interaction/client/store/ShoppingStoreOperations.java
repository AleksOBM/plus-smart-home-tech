package ru.yandex.practicum.commerce.interaction.client.store;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.interaction.requests.SetProductQuantityStateRequest;

import java.util.List;
import java.util.UUID;

public interface ShoppingStoreOperations {

	/**
	 * Получение страницы товаров указанной категории
	 * <p></p>
	 * @param category Категория товаров: Управление, Датчики и т.д.
	 * @param pageNumber Индекс страниц (0..N)
	 *                      </br>Default value : 0
	 * @param pageSize Размер страницы, которая будет возвращена
	 *                    </br>Default value : 20
	 * @param sort Критерии сортировки в формате: property,(asc|desc).
	 *                  </br>По умолчанию порядок сортировки по возрастанию.
	 *                  Поддерживаются несколько критериев сортировки.
	 * @return {@link PageProductDto}
	 */
	@GetMapping
	PageProductDto getSoppingPageByCategory(
			@RequestParam @NotBlank String category,
			@RequestParam(defaultValue = "0") @PositiveOrZero Integer pageNumber,
			@RequestParam(defaultValue = "20") @Positive Integer pageSize,
			@RequestParam @NotNull @NotEmpty List<String> sort
	);

	@PutMapping
	ProductDto addNewProduct(@RequestBody @NotNull @Valid ProductDto productDto);

	@PostMapping
	ProductDto updateProduct(@RequestBody @NotNull @Valid ProductDto productDto)
			throws ProductNotFoundException;

	@PostMapping("/removeProductFromStore")
	boolean removeProductFromStore(@RequestBody @NotNull UUID productId)
			throws ProductNotFoundException;

	@PostMapping("/quantityState")
	boolean setProductState(@RequestBody @NotNull @Valid SetProductQuantityStateRequest request)
			throws ProductNotFoundException;

	@GetMapping("/{productId}")
	ProductDto getProductById(@PathVariable @NotNull UUID productId)
			throws ProductNotFoundException;
}
