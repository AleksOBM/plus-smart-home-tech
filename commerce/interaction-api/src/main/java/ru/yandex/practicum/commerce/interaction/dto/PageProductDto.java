package ru.yandex.practicum.commerce.interaction.dto;

import ru.yandex.practicum.commerce.interaction.utils.PageableObject;
import ru.yandex.practicum.commerce.interaction.utils.SortObject;

public record PageProductDto(
		Long totalElements,
		Integer totalPages,
		Boolean first,
		Boolean last,
		Integer size,
		ProductDto content,
		Integer number,
		SortObject sort,
		Integer numberOfElements,
		PageableObject pageable,
		Boolean empty
) {
}
