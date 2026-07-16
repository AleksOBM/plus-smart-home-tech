package ru.yandex.practicum.commerce.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.interaction.annotations.Loggable;
import ru.yandex.practicum.commerce.interaction.annotations.LoggingAspect;
import ru.yandex.practicum.commerce.interaction.client.store.ShoppingStoreOperations;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.enums.ProductCategory;
import ru.yandex.practicum.commerce.interaction.enums.QuantityState;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.store.fasade.ShoppingStoreFasade;

import java.util.UUID;

@Slf4j
@RestController
@Import(LoggingAspect.class)
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class ShoppingStoreController implements ShoppingStoreOperations {

	private final ShoppingStoreFasade shoppingStoreFasade;

	@Loggable
	@Override
	public PageProductDto getSoppingPageByCategory(
			String category, Pageable pageable
	) {
		var pCat = ProductCategory.of(category);
		return shoppingStoreFasade.getSoppingPageByCategory(pCat, pageable);
	}

	@Loggable
	@Override
	public ProductDto addNewProduct(ProductDto productDto) {
		return shoppingStoreFasade.addNewProduct(productDto);
	}

	@Loggable
	@Override
	public ProductDto updateProduct(@NonNull ProductDto productDto)
			throws ProductNotFoundException {
		if (productDto.productId() == null) {
			throw new RuntimeException("productId is null");
		}
		return shoppingStoreFasade.updateProduct(productDto);
	}

	@Loggable
	@Override
	public boolean removeProductFromStore(UUID productId)
			throws ProductNotFoundException {
		return shoppingStoreFasade.removeProductFromStore(productId);
	}

	@Loggable
	@Override
	public boolean setProductQuantityState(UUID productId,
	                                       QuantityState quantityState
	) throws ProductNotFoundException {
		return shoppingStoreFasade.setProductState(
				productId, quantityState);
	}

	@Loggable
	@Override
	public ProductDto getProductById(UUID productId)
			throws ProductNotFoundException {
		return shoppingStoreFasade.getProductById(productId);
	}

}
