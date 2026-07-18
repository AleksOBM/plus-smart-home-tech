package ru.yandex.practicum.commerce.interaction.client.warehouse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.interaction.dto.AddressDto;
import ru.yandex.practicum.commerce.interaction.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.interaction.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.interaction.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.interaction.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.interaction.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.interaction.requests.AddProductToWarehouseRequest;
import ru.yandex.practicum.commerce.interaction.requests.NewProductInWarehouseRequest;

@Slf4j
@Component
public class WarehouseClientFallback implements
		WarehouseClient, WarehouseOperations {

	@Override
	public void createNewProduct(NewProductInWarehouseRequest request)
			throws SpecifiedProductAlreadyInWarehouseException {
		log.info("WarehouseClientFallback.addProduct()");
	}

	@Override
	public BookedProductsDto checkProductQuantity(ShoppingCartDto cartDto)
			throws ProductInShoppingCartLowQuantityInWarehouse {
		log.info("WarehouseClientFallback.checkProduct()");
		return null;
	}

	@Override
	public void addProductQuantity(AddProductToWarehouseRequest request)
			throws NoSpecifiedProductInWarehouseException {
		log.info("WarehouseClientFallback.takeProduct()");
	}

	@Override
	public AddressDto getWarehouseAddress() {
		log.info("WarehouseClientFallback.getAddress()");
		return null;
	}
}
