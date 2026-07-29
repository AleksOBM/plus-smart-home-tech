package ru.yandex.practicum.product.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.product.dto.CreateProductRequest;
import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.dto.UpdateProductRequest;
import ru.yandex.practicum.product.service.ProductService;

import java.util.List;

@Slf4j
@SuppressWarnings("similarLog")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * Получить все товары
	 *
	 * @return List.of({@link ProductDto}) - Возвращает список всех активных товаров
	 */
	@GetMapping
	public List<ProductDto> getAllProducts() {
		log.info("GET /api/products");
		return productService.getAllProducts();
	}

	/**
	 * Получить товар по ID
	 *
	 * @param id ID товара
	 * @return {@link ProductDto} - Возвращает подробную информацию о товаре
	 */
	@GetMapping("/{id}")
	public ProductDto getProductById(@PathVariable Long id) {
		log.info("GET /api/products/{}", id);
		return productService.getProductById(id);
	}

	/**
	 * Товары по категории
	 *
	 * @param categoryId ID категории
	 * @return List.of({@link ProductDto}) - Возвращает товары указанной категории
	 */
	@GetMapping("/category/{categoryId}")
	public List<ProductDto> getProductsByCategory(@PathVariable Long categoryId) {
		log.info("GET /api/products/category/{}", categoryId);
		return productService.getProductsByCategory(categoryId);
	}

	/**
	 * Поиск товаров
	 *
	 * @param query Поисковый запрос
	 * @return List.of({@link ProductDto}) - Поиск товаров по названию (частичное совпадение)
	 */
	@GetMapping("/search")
	public List<ProductDto> searchProducts(@RequestParam String query) {
		log.info("GET /api/products/search\nparam:{}", query);
		return productService.searchProducts(query);
	}

	/**
	 * Создать товар
	 * <br/>
	 * Добавляет новый товар в каталог
	 *
	 * @param request Запрос на создание товара
	 * @return {@link ProductDto} - Информация о товаре
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDto createProduct(@Valid @RequestBody CreateProductRequest request) {
		log.info("POST /api/products\nbody:{}", request);
		return productService.createProduct(request);
	}

	/**
	 * Обновить товар
	 * <br/>
	 * Частичное обновление информации о товаре
	 *
	 * @param id      ID товара
	 * @param request Запрос на обновление товара
	 * @return {@link ProductDto} - Информация о товаре
	 */
	@PatchMapping("/{id}")
	public ProductDto updateProduct(@PathVariable @NotNull Long id,
	                                @Valid @RequestBody UpdateProductRequest request) {
		log.info("PATCH /api/products/{}\nbody:{}", id, request);
		return productService.updateProduct(id, request);
	}
}