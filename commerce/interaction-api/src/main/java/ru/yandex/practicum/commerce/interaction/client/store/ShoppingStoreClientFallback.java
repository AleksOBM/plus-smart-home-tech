package ru.yandex.practicum.commerce.interaction.client.store;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.interaction.requests.SetProductQuantityStateRequest;

import java.util.List;
import java.util.UUID;

@Slf4j
public class ShoppingStoreClientFallback implements
		ShoppingStoreClient, ShoppingStoreOperations {

	@Override
	public PageProductDto getSoppingPageByCategory(String category, Integer pageNumber,
	                                               Integer pageSize, List<String> sort
	) {
		log.info("Fallback to getSoppingPageByCategory");
		return null;
	}

	@Override
	public ProductDto addNewProduct(ProductDto productDto) {
		log.info("Fallback to addNewProduct");
		return null;
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto)
			throws ProductNotFoundException {
		log.info("Fallback to updateProduct");
		return null;
	}

	@Override
	public boolean removeProductFromStore(UUID productId)
			throws ProductNotFoundException {
		log.info("Fallback to removeProductFromStore");
		return false;
	}

	@Override
	public boolean setProductState(SetProductQuantityStateRequest request)
			throws ProductNotFoundException {
		log.info("Fallback to setProductState");
		return false;
	}

	@Override
	public ProductDto getProductById(UUID productId)
			throws ProductNotFoundException {
		log.info("Fallback to getProductById");
		return null;
	}
}
