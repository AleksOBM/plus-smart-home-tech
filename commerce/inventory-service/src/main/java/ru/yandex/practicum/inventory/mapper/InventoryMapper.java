package ru.yandex.practicum.inventory.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.inventory.dto.InventoryDto;
import ru.yandex.practicum.inventory.entity.InventoryRecord;

@UtilityClass
public class InventoryMapper {

	public InventoryDto toDto(@NonNull InventoryRecord record) {
		return InventoryDto.builder()
				.id(record.getId())
				.productId(record.getProductId())
				.quantity(record.getQuantity())
				.reservedQuantity(record.getReservedQuantity())
				.availableQuantity(record.getAvailableQuantity())
				.build();
	}
}
