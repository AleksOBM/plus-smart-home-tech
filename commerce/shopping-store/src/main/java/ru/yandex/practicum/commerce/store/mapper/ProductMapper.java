package ru.yandex.practicum.commerce.store.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.commerce.interaction.dto.ProductDto;
import ru.yandex.practicum.commerce.store.model.Product;

@UtilityClass
public class ProductMapper {

	public ProductDto toDto(@NonNull Product product) {
		return ProductDto.builder()
				.productId(product.getProductId())
				.productName(product.getProductName())
				.description(product.getDescription())
				.imageSrc(product.getImageSrc())
				.quantityState(product.getQuantityState())
				.productState(product.getProductState())
				.productCategory(product.getProductCategory())
				.price(product.getPrice())
				.build();
	}

	public Product toEntity(@NonNull ProductDto productDto) {
		return Product.builder()
				.productId(productDto.productId())
				.productName(productDto.productName())
				.description(productDto.description())
				.imageSrc(productDto.imageSrc())
				.quantityState(productDto.quantityState())
				.productState(productDto.productState())
				.productCategory(productDto.productCategory())
				.price(productDto.price())
				.build();
	}

	public Product updateEntity(@NonNull ProductDto productDto, @NonNull Product oldProduct) {
		return oldProduct.toBuilder()
				.productId(productDto.productId())
				.productName(productDto.productName() != null ?
						productDto.productName() : oldProduct.getProductName())
				.description(productDto.description() != null ?
						productDto.description() : oldProduct.getDescription())
				.imageSrc(productDto.imageSrc() != null ?
						productDto.imageSrc() : oldProduct.getImageSrc())
				.quantityState(productDto.quantityState() != null ?
						productDto.quantityState() : oldProduct.getQuantityState())
				.productState(productDto.productState() != null ?
						productDto.productState() : oldProduct.getProductState())
				.productCategory(productDto.productCategory() != null ?
						productDto.productCategory() : oldProduct.getProductCategory())
				.price(productDto.price() != null ?
						productDto.price() : oldProduct.getPrice())
				.build();
	}
}
