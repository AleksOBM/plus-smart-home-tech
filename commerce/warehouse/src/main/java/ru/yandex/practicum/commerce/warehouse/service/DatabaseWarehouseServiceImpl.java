package ru.yandex.practicum.commerce.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.interaction.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.interaction.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.interaction.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.interaction.requests.AddProductToWarehouseRequest;
import ru.yandex.practicum.commerce.interaction.requests.NewProductInWarehouseRequest;
import ru.yandex.practicum.commerce.warehouse.mapper.DimensionMapper;
import ru.yandex.practicum.commerce.warehouse.model.ProductQuantityProjection;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;
import ru.yandex.practicum.commerce.warehouse.repository.ProductRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DatabaseWarehouseServiceImpl
		implements DatabaseWarehouseService {

	private final ProductRepository productRepository;

	@Override
	public void createNewProduct(@NonNull NewProductInWarehouseRequest request) {
		if (productRepository.existsById(request.productId())) {
			throw new SpecifiedProductAlreadyInWarehouseException(
					request.productId().toString()
			);
		}

		productRepository.save(WarehouseProduct.builder()
				.productId(request.productId())
				.fragile(request.fragile())
				.weight(request.weight())
				.dimension(DimensionMapper.toEntity(request.dimension()))
				.build()
		);
	}

	@Override
	public BookedProductsDto checkProductQuantity(@NonNull ShoppingCartDto cartDto) {
		Set<UUID> ids = cartDto.products().keySet();
		List<ProductQuantityProjection> freeProducts = productRepository
				.findFreeQuantities(ids);

		Map<UUID, Integer> failedProducts = new HashMap<>();
		cartDto.products().forEach((productId, productQuantity) -> {
			for (ProductQuantityProjection projection : freeProducts) {
				if (projection.productId().equals(productId)) {
					var result = projection.quantityFree() - productQuantity;
					if (result < 0) {
						failedProducts.put(productId, result * -1);
					}
				}
			}
		});

		if (!failedProducts.isEmpty()) {
			throw new ProductInShoppingCartLowQuantityInWarehouse(failedProducts);
		}

		List<WarehouseProduct> warehouseProducts = productRepository.findAllById(ids);

		return BookedProductsDto.builder()
				.deliveryVolume(warehouseProducts.stream()
						.map(WarehouseProduct::getDimension)
						.map(dimension ->
								dimension.getWidth() * dimension.getHeight() * dimension.getDepth()
						)
						.reduce(0.0f, Float::sum)
				)
				.deliveryWeight(warehouseProducts.stream()
						.map(WarehouseProduct::getWeight)
						.reduce(0.0f, Float::sum)
				)
				.fragile(warehouseProducts.stream()
						.map(WarehouseProduct::getFragile)
						.anyMatch(fragile -> fragile == true)
				)
				.build();
	}

	@Override
	public void addProductQuantity(@NonNull AddProductToWarehouseRequest request) {
		WarehouseProduct product = productRepository
				.findById(request.productId())
				.orElseThrow(() ->
						new NoSpecifiedProductInWarehouseException(request.productId())
				);

		productRepository.save(product.toBuilder()
				.quantityAll(product.getQuantityAll() + request.quantity())
				.build()
		);
	}
}
