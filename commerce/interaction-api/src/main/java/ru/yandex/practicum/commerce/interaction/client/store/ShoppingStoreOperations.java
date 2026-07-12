package ru.yandex.practicum.commerce.interaction.client.store;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.interaction.requests.SetProductQuantityStateRequest;

import java.util.List;
import java.util.UUID;

public interface ShoppingStoreOperations {

	@GetMapping
	PageProductDto getSoppingPageByCategory(String category, Integer pageNumber,
	                                        Integer pageSize, List<String> sort
	);

	@PutMapping
	ProductDto addNewProduct(@RequestBody ProductDto productDto);

	@PostMapping
	ProductDto updateProduct(@RequestBody ProductDto productDto)
			throws ProductNotFoundException;

	@PostMapping("removeProductFromStore")
	boolean removeProductFromStore(@RequestBody UUID productId)
			throws ProductNotFoundException;

	@PostMapping("quantityState")
	boolean setProductState(@RequestBody SetProductQuantityStateRequest request)
			throws ProductNotFoundException;

	@GetMapping("{productId}")
	ProductDto getProductById(@PathVariable UUID productId)
			throws ProductNotFoundException;
}
