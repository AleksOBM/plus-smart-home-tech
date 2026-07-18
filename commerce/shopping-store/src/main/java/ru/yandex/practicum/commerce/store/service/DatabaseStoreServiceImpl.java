package ru.yandex.practicum.commerce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.enums.ProductCategory;
import ru.yandex.practicum.commerce.interaction.enums.ProductState;
import ru.yandex.practicum.commerce.interaction.enums.QuantityState;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.store.mapper.ProductMapper;
import ru.yandex.practicum.commerce.store.model.Product;
import ru.yandex.practicum.commerce.store.repository.ProductRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DatabaseStoreServiceImpl implements DatabaseStoreService {

	private final ProductRepository productRepository;

	@Override
	public PageProductDto getSoppingPageByCategory(ProductCategory category,
	                                               Pageable pageable) {
		Page<ProductDto> page = productRepository
				.findByProductCategory(category, pageable)
				.map(ProductMapper::toDto);

		return PageProductDto.from(page);
	}

	@Override
	public ProductDto createNewProductInShoppingStore(ProductDto productDto) {
		Product product = productRepository
				.save(ProductMapper.toEntity(productDto));
		return ProductMapper.toDto(product);
	}

	@Override
	public ProductDto updateProduct(@NonNull ProductDto productDto) {
		Product oldProduct = findProductById(productDto.productId());
		Product newProduct = ProductMapper
				.updateEntity(productDto, oldProduct);

		return ProductMapper
				.toDto(productRepository.save(newProduct));
	}

	@Override
	public boolean removeProductFromStore(UUID productId)
			throws ProductNotFoundException {
		Product oldProduct = findProductById(productId);
		return productRepository.save(
				oldProduct.toBuilder().productState(ProductState.DEACTIVATE).build()
		).getProductState().equals(ProductState.DEACTIVATE);
	}

	@Override
	public boolean setProductState(UUID productId, QuantityState quantityState) {
		Product product = findProductById(productId);
		Product updatedProduct = productRepository
				.save(product.toBuilder()
						.quantityState(quantityState)
						.build());
		return updatedProduct.getQuantityState() == quantityState;
	}

	@Override
	public ProductDto getProductById(UUID productId) {
		Product product = findProductById(productId);
		return ProductMapper.toDto(product);
	}

	@NonNull
	private Product findProductById(UUID productId) {
		return productRepository
				.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(
						"Продукт с id: " + productId + " не найден"));
	}
}
