package ru.yandex.practicum.product.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.product.dto.CategoryDto;
import ru.yandex.practicum.product.entity.Category;

@UtilityClass
public class CategoryMapper {

	public CategoryDto toDto(@NonNull Category category) {
		return CategoryDto.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();
	}
}
