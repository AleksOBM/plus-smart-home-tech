package ru.yandex.practicum.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.inventory.dto.InventoryDto;
import ru.yandex.practicum.inventory.dto.ReserveRequest;
import ru.yandex.practicum.inventory.dto.ReserveResponse;
import ru.yandex.practicum.inventory.dto.UpdateInventoryRequest;
import ru.yandex.practicum.inventory.entity.InventoryRecord;
import ru.yandex.practicum.inventory.exception.InsufficientStockException;
import ru.yandex.practicum.inventory.exception.NotFoundException;
import ru.yandex.practicum.inventory.mapper.InventoryMapper;
import ru.yandex.practicum.inventory.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepository;

	@Override
	public List<InventoryDto> getFullInventory() {
		return inventoryRepository.findAll().stream()
				.map(InventoryMapper::toDto)
				.toList();
	}

	@Override
	public InventoryDto getInventoryRecord(Long productId) {
		return InventoryMapper.toDto(findByProductId(productId));
	}

	@Override
	public InventoryDto updateInventoryRecord(@NonNull UpdateInventoryRequest request) {
		InventoryRecord record = findByProductId(request.productId());
		if (record.getReservedQuantity() > request.quantity()) {
			throw new InsufficientStockException(
					"Указанного количества не достаточно для обеспечения резерва"
			);
		}
		record.setQuantity(request.quantity());
		return InventoryMapper.toDto(inventoryRepository.save(record));
	}

	@Override
	public InventoryDto createInventoryRecord(@NonNull UpdateInventoryRequest request) {
		return InventoryMapper.toDto(
				inventoryRepository.save(InventoryRecord.builder()
						.productId(request.productId())
						.quantity(request.quantity())
						.build()
				)
		);
	}

	@Override
	public ReserveResponse reserveProduct(@NonNull ReserveRequest request) {
		InventoryRecord record = findByProductId(request.productId());
		int availableQuantity = record.getAvailableQuantity();
		if (availableQuantity < request.quantity()) {
			throw new InsufficientStockException("Не достаточно товара для резерва");
		}

		record.setReservedQuantity(record.getReservedQuantity() + request.quantity());
		InventoryRecord newRecord = inventoryRepository.save(record);

		return ReserveResponse.builder()
				.success(true)
				.availableQuantity(newRecord.getAvailableQuantity())
				.message("OK")
				.build();
	}

	@NonNull
	private InventoryRecord findByProductId(@NonNull Long productId) {
		return inventoryRepository.findByProductId(productId).orElseThrow(
				() -> new NotFoundException("Продукт с id: " + productId + " не найден")
		);
	}

}
