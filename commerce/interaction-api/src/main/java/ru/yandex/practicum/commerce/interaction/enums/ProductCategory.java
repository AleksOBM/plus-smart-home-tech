package ru.yandex.practicum.commerce.interaction.enums;

import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/// Категория товара
public enum ProductCategory {
	UNDEFINED, LIGHTING, CONTROL, SENSORS;

	@NonNull
	public static ProductCategory of(String text) {
		String category = Arrays.stream(ProductCategory.values())
				.map(ProductCategory::name)
				.filter(value -> text.toUpperCase().equals(value))
				.findAny().orElse(null);

		if (category == null) {
			throw new RuntimeException("Категория " + text + " не найдена");
		}
		return ProductCategory.valueOf(category);
	}

	@NonNull
	@SuppressWarnings("all")
	public static List<String> getValidValues() {
		return Arrays.stream(values()).filter(v -> v.ordinal() > 0)
				.map(ProductCategory::name)
				.collect(Collectors.toUnmodifiableList());
	}
}
