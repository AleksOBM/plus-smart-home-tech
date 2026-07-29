package ru.yandex.practicum.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.product.dto.CategoryDto;
import ru.yandex.practicum.product.dto.CreateCategoryRequest;
import ru.yandex.practicum.product.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	/**
	 * Получить все категории
	 * @return List.of({@link CategoryDto}) - Возвращает список всех категорий товаров
	 */
	@GetMapping
	public List<CategoryDto> getAllCategories() {
		log.info("GET /api/categories");
		return categoryService.getAllCategories();
	}

	/**
	 * Получить категорию по ID
	 * @param id ID категории
	 * @return {@link CategoryDto} - Категория товаров
	 */
	@GetMapping("/{id}")
	public CategoryDto getCategoryById(@PathVariable Long id) {
		log.info("GET /api/categories/{}", id);
		return categoryService.getCategoryById(id);
	}

	/**
	 * Создать категорию
	 * @param request Запрос на создание категории
	 * @return {@link CategoryDto} - Создаёт новую категорию товаров
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryDto createCategory(@Valid @RequestBody CreateCategoryRequest request) {
		log.info("POST /api/categories\nbody:{}", request);
		return categoryService.createCategory(request);
	}
}