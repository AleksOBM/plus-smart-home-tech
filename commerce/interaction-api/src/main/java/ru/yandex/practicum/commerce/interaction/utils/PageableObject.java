package ru.yandex.practicum.commerce.interaction.utils;

public record PageableObject(
		Long offset,
		SortObject sort,
		Boolean unpaged,
		Boolean paged,
		Integer pageSize
) {
}
