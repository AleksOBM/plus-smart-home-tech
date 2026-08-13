package ru.yandex.practicum.inventory.service;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.inventory.dto.InventoryDto;
import ru.yandex.practicum.inventory.dto.ReserveRequest;
import ru.yandex.practicum.inventory.dto.ReserveResponse;
import ru.yandex.practicum.inventory.dto.UpdateInventoryRequest;

import java.util.List;

@Transactional
public interface InventoryService {

	@Transactional(readOnly = true)
	List<InventoryDto> getFullInventory();

	@Transactional(readOnly = true)
	InventoryDto getInventoryRecord(Long productId);

	InventoryDto updateInventoryRecord(UpdateInventoryRequest request);

	InventoryDto createInventoryRecord(UpdateInventoryRequest request);

	ReserveResponse reserveProduct(ReserveRequest request);

	ReserveResponse releaseProduct(ReserveRequest request);
}
