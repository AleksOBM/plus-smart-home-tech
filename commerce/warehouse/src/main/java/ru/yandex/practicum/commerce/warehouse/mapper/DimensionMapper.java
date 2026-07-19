package ru.yandex.practicum.commerce.warehouse.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.commerce.interaction.dto.DimensionDto;
import ru.yandex.practicum.commerce.warehouse.model.Dimension;

@UtilityClass
@SuppressWarnings("unused")
public class DimensionMapper {

	public Dimension toEntity(@NonNull DimensionDto dto) {
		return Dimension.builder()
				.width(dto.width())
				.height(dto.height())
				.depth(dto.depth())
				.build();
	}

	public DimensionDto toDto(@NonNull Dimension dimension) {
		return DimensionDto.builder()
				.width(dimension.getWidth())
				.height(dimension.getHeight())
				.depth(dimension.getDepth())
				.build();
	}
}
