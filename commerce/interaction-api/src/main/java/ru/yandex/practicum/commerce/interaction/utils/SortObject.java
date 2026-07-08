package ru.yandex.practicum.commerce.interaction.utils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SortObject {
	String direction;
	String nullHandling;
	Boolean ascending;
	String property;
	Boolean ignoreCase;
	Integer pageNumber;
}
