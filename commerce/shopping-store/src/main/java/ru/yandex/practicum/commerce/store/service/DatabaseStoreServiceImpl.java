package ru.yandex.practicum.commerce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.interaction.dto.PageProductDto;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.interaction.enums.ProductCategory;
import ru.yandex.practicum.commerce.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.store.mapper.ProductMapper;
import ru.yandex.practicum.commerce.store.model.Product;
import ru.yandex.practicum.commerce.store.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DatabaseStoreServiceImpl implements DatabaseStoreService {

	private final ProductRepository productRepository;

	@Override
	public PageProductDto getSoppingPageByCategory(ProductCategory category,
	                                               Pageable pageable) {
		Page<Product> productPage = productRepository
				.findByProductCategory(category, pageable);

		List<ProductDto> productDtos = productPage
				.getContent().stream()
				.map(ProductMapper::toDto)
				.toList();

		return PageProductDto.builder()
				.content(productDtos)
				.pageable(pageable)
				.build();
	}

	@Override
	public ProductDto addNewProduct(ProductDto productDto) {
		Product product = productRepository.save(ProductMapper.toEntity(productDto));
		return ProductMapper.toDto(product);
	}

	@Override
	public ProductDto updateProduct(@NonNull ProductDto productDto) {
		Product oldProduct = productRepository.findById(productDto.productId())
				.orElseThrow();
		Product newProduct = ProductMapper.updateEntity(productDto, oldProduct);

		return ProductMapper.toDto(productRepository.save(newProduct));
	}

	@Override
	public boolean removeProductFromStore(UUID productId) throws ProductNotFoundException {
		if (!productRepository.existsById(productId)) {
			throw new ProductNotFoundException("Продукт с id: " + productId + " не найден");
		}

		return productRepository.deleteProductByProductId(productId) > 0;
	}
}
