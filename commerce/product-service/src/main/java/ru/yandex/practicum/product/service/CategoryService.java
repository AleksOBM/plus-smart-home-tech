package ru.yandex.practicum.product.service;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.product.dto.CategoryDto;
import ru.yandex.practicum.product.dto.CreateCategoryRequest;

import java.util.List;

@Transactional(readOnly = true)
public interface CategoryService {

	List<CategoryDto> getAllCategories();

	CategoryDto getCategoryById(Long id);

	@Transactional
	CategoryDto createCategory(CreateCategoryRequest request);
}
