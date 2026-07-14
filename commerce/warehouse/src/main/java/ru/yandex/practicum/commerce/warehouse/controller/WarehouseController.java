package ru.yandex.practicum.commerce.warehouse.controller;

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

@Slf4j
@RestController
@Import(LoggingAspect.class)
@RequestMapping("/api/v1/warehouse")
public class WarehouseController implements WarehouseOperations {

	@Loggable
	@Override
	public void addProduct(NewProductInWarehouseRequest request)
			throws SpecifiedProductAlreadyInWarehouseException {
	}

	@Loggable
	@Override
	public BookedProductsDto checkProduct(ShoppingCartDto cartDto)
			throws ProductInShoppingCartLowQuantityInWarehouse {
		return null;
	}

	@Loggable
	@Override
	public void takeProduct(AddProductToWarehouseRequest request)
			throws NoSpecifiedProductInWarehouseException {
	}

	@Loggable
	@Override
	public AddressDto getAddress() {
		return null;
	}
}
