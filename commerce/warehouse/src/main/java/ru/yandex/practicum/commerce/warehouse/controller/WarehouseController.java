package ru.yandex.practicum.commerce.warehouse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.interaction.client.warehouse.WarehouseOperations;
import ru.yandex.practicum.commerce.interaction.dto.AddressDto;
import ru.yandex.practicum.commerce.interaction.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.interaction.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.interaction.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.interaction.requests.AddProductToWarehouseRequest;
import ru.yandex.practicum.commerce.interaction.requests.NewProductInWarehouseRequest;

@RestController
@RequestMapping("/api/v1/warehouse/")
public class WarehouseController implements WarehouseOperations {

	@Override
	public void addProduct(NewProductInWarehouseRequest request)
			throws SpecifiedProductAlreadyInWarehouseException {
	}

	@Override
	public BookedProductsDto checkProduct(ShoppingCartDto cartDto)
			throws ProductInShoppingCartLowQuantityInWarehouse {
		return null;
	}

	@Override
	public void takeProduct(AddProductToWarehouseRequest request)
			throws NoSpecifiedProductInWarehouseException {
	}

	@Override
	public AddressDto getAddress() {
		return null;
	}
}
