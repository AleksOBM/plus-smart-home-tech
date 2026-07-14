package ru.yandex.practicum.commerce.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.interaction.annotations.Loggable;
import ru.yandex.practicum.commerce.interaction.annotations.LoggingAspect;
import ru.yandex.practicum.commerce.interaction.client.store.ShoppingStoreOperations;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.enums.ProductCategory;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.interaction.requests.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.store.fasade.ShoppingStoreFasade;

import java.util.List;
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
			String category, Integer pageNumber,
			Integer pageSize, List<String> sort
	) {
		var pCat = ProductCategory.of(category);
		var pSort = parseSortParams(sort);
		var pageable = PageRequest.of(pageNumber, pageSize, pSort);
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
	public boolean setProductState(SetProductQuantityStateRequest request)
			throws ProductNotFoundException {
		return false;
	}

	@Loggable
	@Override
	public ProductDto getProductById(UUID productId)
			throws ProductNotFoundException {
		return null;
	}

	@NonNull
	private Sort parseSortParams(@NonNull List<String> sortParams) {
		List<Sort.Order> orders = sortParams.stream()
				.filter(param -> param != null && param.contains(","))
				.map(param -> {
					String[] parts = param.split(",");
					if (parts.length != 2) {
						throw new IllegalArgumentException("Invalid sort format: " + param);
					}
					return new Sort.Order(
							Sort.Direction.fromString(parts[1].trim()),
							parts[0].trim()
					);
				})
				.toList();

		return Sort.by(orders);
	}
}
