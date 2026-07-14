package ru.yandex.practicum.commerce.interaction.client.warehouse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.commerce.interaction.dto.AddressDto;
import ru.yandex.practicum.commerce.interaction.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.interaction.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.interaction.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.interaction.requests.AddProductToWarehouseRequest;
import ru.yandex.practicum.commerce.interaction.requests.NewProductInWarehouseRequest;

public interface WarehouseOperations {

	@PutMapping
	void addProduct(@RequestBody NewProductInWarehouseRequest request)
			throws SpecifiedProductAlreadyInWarehouseException;

	@PostMapping("/check")
	BookedProductsDto checkProduct(@RequestBody ShoppingCartDto cartDto)
			throws ProductInShoppingCartLowQuantityInWarehouse;

	@PostMapping("/add")
	void takeProduct(@RequestBody AddProductToWarehouseRequest request)
			throws NoSpecifiedProductInWarehouseException;

	@GetMapping("/address")
	AddressDto getAddress();

}
