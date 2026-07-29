package ru.yandex.practicum.product.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.product.dto.ProductDto;
import ru.yandex.practicum.product.entity.Product;

@UtilityClass
public class ProductMapper {

	public ProductDto toDto(@NonNull Product product) {
		return ProductDto.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.category(CategoryMapper.toDto(product.getCategory()))
				.imageUrl(product.getImageUrl())
				.active(product.getActive())
				.build();
	}
}
