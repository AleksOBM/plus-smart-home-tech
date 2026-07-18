package ru.yandex.practicum.commerce.warehouse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.interaction.annotations.Loggable;
import ru.yandex.practicum.commerce.interaction.annotations.LoggingAspect;
import ru.yandex.practicum.commerce.interaction.client.warehouse.WarehouseOperations;
import ru.yandex.practicum.commerce.interaction.dto.AddressDto;
import ru.yandex.practicum.commerce.interaction.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.interaction.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.interaction.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.interaction.requests.AddProductToWarehouseRequest;
import ru.yandex.practicum.commerce.interaction.requests.NewProductInWarehouseRequest;
import ru.yandex.practicum.commerce.warehouse.fasade.WarehouseFasade;
import ru.yandex.practicum.commerce.warehouse.repository.AddressRepository;

@Slf4j
@RestController
@Import(LoggingAspect.class)
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController implements WarehouseOperations {

	private final WarehouseFasade warehouseFasade;

	@Loggable
	@Override
	public void createNewProduct(NewProductInWarehouseRequest request)
			throws SpecifiedProductAlreadyInWarehouseException {
		warehouseFasade.createNewProduct(request);
	}

	@Loggable
	@Override
	public BookedProductsDto checkProductQuantity(ShoppingCartDto cartDto)
			throws ProductInShoppingCartLowQuantityInWarehouse {
		return warehouseFasade.checkProductQuantity(cartDto);
	}

	@Loggable
	@Override
	public void addProductQuantity(AddProductToWarehouseRequest request)
			throws NoSpecifiedProductInWarehouseException {
		warehouseFasade.addProductQuantity(request);
	}

	@Loggable
	@Override
	public AddressDto getWarehouseAddress() {
		String address = AddressRepository.CURRENT_ADDRESS;
		return AddressDto.builder()
				.country(address)
				.city(address)
				.street(address)
				.house(address)
				.flat(address)
				.build();
	}
}
