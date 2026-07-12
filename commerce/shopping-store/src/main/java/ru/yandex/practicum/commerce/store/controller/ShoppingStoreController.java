package ru.yandex.practicum.commerce.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.interaction.client.store.ShoppingStoreOperations;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.interaction.requests.SetProductQuantityStateRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-store/")
public class ShoppingStoreController implements ShoppingStoreOperations {

	@Override
	public PageProductDto getSoppingPageByCategory(
			String category, Integer pageNumber,
			Integer pageSize, List<String> sort
	) {
		return null;
	}

	@Override
	public ProductDto addNewProduct(ProductDto productDto) {
		return null;
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto)
			throws ProductNotFoundException {
		return null;
	}

	@Override
	public boolean removeProductFromStore(UUID productId)
			throws ProductNotFoundException {
		return false;
	}

	@Override
	public boolean setProductState(SetProductQuantityStateRequest request)
			throws ProductNotFoundException {
		return false;
	}

	@Override
	public ProductDto getProductById(UUID productId)
			throws ProductNotFoundException {
		return null;
	}
}
