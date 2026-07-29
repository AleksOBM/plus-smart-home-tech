package ru.yandex.practicum.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.product.dto.CreateProductRequest;
import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.dto.UpdateProductRequest;
import ru.yandex.practicum.product.entity.Category;
import ru.yandex.practicum.product.entity.Product;
import ru.yandex.practicum.product.exception.NotFoundException;
import ru.yandex.practicum.product.mapper.ProductMapper;
import ru.yandex.practicum.product.repository.CategoryRepository;
import ru.yandex.practicum.product.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public List<ProductDto> getAllProducts() {
		return productRepository.findAllByActiveTrue().stream()
				.map(ProductMapper::toDto)
				.toList();
	}

	@Override
	public ProductDto getProductById(Long id) {
		return ProductMapper.toDto(findProductById(id));
	}

	@Override
	public List<ProductDto> getProductsByCategory(Long categoryId) {
		return productRepository.findByCategoryIdAndActiveTrue(categoryId).stream()
				.map(ProductMapper::toDto)
				.toList();
	}

	@Override
	public List<ProductDto> searchProducts(String query) {
		return productRepository.searchByName(query).stream()
				.map(ProductMapper::toDto)
				.toList();
	}

	@Override
	public ProductDto createProduct(@NonNull CreateProductRequest request) {
		Category category = findCategoryById(request.categoryId());

		return ProductMapper.toDto(
				productRepository.save(
						Product.builder()
								.name(request.name())
								.description(request.description())
								.price(request.price())
								.category(category)
								.imageUrl(request.imageUrl())
								.build()
				)
		);
	}

	@Override
	public ProductDto updateProduct(Long id, @NonNull UpdateProductRequest request) {
		Product product = findProductById(id);
		Category category = request.categoryId() == null ? null : findCategoryById(request.categoryId());
		Product newProduct = Product.builder()
				.id(id)
				.name(request.name() == null ? product.getName() : request.name())
				.description(request.description() == null ? product.getDescription() : request.description())
				.price(request.price() == null ? product.getPrice() : request.price())
				.category(category == null ? product.getCategory() : category)
				.active(request.active() == null ? product.getActive() : request.active())
				.imageUrl(request.imageUrl() == null ? product.getImageUrl() : request.imageUrl())
				.build();

		return ProductMapper.toDto(productRepository.save(newProduct));
	}

	@NonNull
	private Product findProductById(long id) {
		return productRepository.findById(id).orElseThrow(() ->
				new NotFoundException(String.format("Товар с id %s не найден", id))
		);
	}

	@NonNull
	private Category findCategoryById(long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(
								String.format("Категория с id %s не найдена", id)
						)
				);
	}
}
