package ru.yandex.practicum.product.service;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.product.dto.CreateProductRequest;
import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.dto.UpdateProductRequest;

import java.util.List;

@Transactional(readOnly = true)
public interface ProductService {

	List<ProductDto> getAllProducts();

	ProductDto getProductById(Long id);

	List<ProductDto> getProductsByCategory(Long categoryId);

	List<ProductDto> searchProducts(String query);

	@Transactional
	ProductDto createProduct(CreateProductRequest request);

	@Transactional
	ProductDto updateProduct(Long id, UpdateProductRequest request);
}
