package ru.yandex.practicum.commerce.store.service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.enums.ProductCategory;
import ru.yandex.practicum.commerce.interaction.enums.QuantityState;

import java.util.UUID;

@Transactional
public interface DatabaseStoreService {

	@Transactional(readOnly = true)
	PageProductDto getSoppingPageByCategory(
			ProductCategory category, Pageable pageable);

	ProductDto addNewProduct(ProductDto productDto);

	ProductDto updateProduct(ProductDto productDto);

	boolean removeProductFromStore(UUID productId);

	boolean setProductState(UUID uuid, QuantityState quantityState);

	ProductDto getProductById(UUID productId);
}
