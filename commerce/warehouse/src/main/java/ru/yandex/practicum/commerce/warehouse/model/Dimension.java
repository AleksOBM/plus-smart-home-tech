package ru.yandex.practicum.commerce.warehouse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

/// Размеры товара
@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Dimension {

	@Column(table = "dimensions", nullable = false)
	Float width;

	@Column(table = "dimensions", nullable = false)
	Float height;

	@Column(table = "dimensions", nullable = false)
	Float depth;
}