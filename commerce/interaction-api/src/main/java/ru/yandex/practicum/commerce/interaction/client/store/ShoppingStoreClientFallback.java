package ru.yandex.practicum.commerce.interaction.client.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.enums.QuantityState;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;

import java.util.UUID;

@Slf4j
@Component
public class ShoppingStoreClientFallback implements
		ShoppingStoreClient, ShoppingStoreOperations {

	@Override
	public PageProductDto getSoppingPageByCategory(String category,
	                                               Pageable pageable
	) {
		log.info("Fallback to getSoppingPageByCategory");
		return null;
	}

	@Override
	public ProductDto createNewProduct(ProductDto productDto) {
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
	public boolean setProductQuantityState(UUID productId,
	                                       QuantityState quantityState)
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
