package ru.yandex.practicum.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.product.dto.CategoryDto;
import ru.yandex.practicum.product.dto.CreateCategoryRequest;
import ru.yandex.practicum.product.entity.Category;
import ru.yandex.practicum.product.exception.NotFoundException;
import ru.yandex.practicum.product.mapper.CategoryMapper;
import ru.yandex.practicum.product.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream()
				.map(CategoryMapper::toDto)
				.toList();
	}

	@Override
	public CategoryDto getCategoryById(Long id) {
		return CategoryMapper.toDto(categoryRepository.findById(id).orElseThrow(() ->
						new NotFoundException(String.format("Категория с id %s не найдена", id))
				)
		);
	}

	@Override
	public CategoryDto createCategory(@NonNull CreateCategoryRequest request) {
		return CategoryMapper.toDto(categoryRepository.save(Category.builder()
						.name(request.name())
						.description(request.description())
						.build()
				)
		);
	}
}
