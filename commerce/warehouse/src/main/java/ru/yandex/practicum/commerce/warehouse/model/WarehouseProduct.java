package ru.yandex.practicum.commerce.warehouse.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/// Описание продукта для учета на складе
@Getter
@Entity
@NoArgsConstructor(force = true)
@SuperBuilder(toBuilder = true)
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SecondaryTable(
		name = "dimensions",
		pkJoinColumns = @PrimaryKeyJoinColumn(
				name = "product_id",
				referencedColumnName = "product_id"
		)
)
public class WarehouseProduct {

	/// Идентификатор товара в БД
	@Id
	@Column(name = "product_id")
	UUID productId;

	/// Остаток товара на складе
	@Builder.Default
	@Column(nullable = false)
	Integer quantityAll = 0;

	/// Количество забронированного товара
	@Builder.Default
	@Column(nullable = false)
	Integer quantityBooked = 0;

	/// Хрупкость
	@Column(nullable = false)
	Boolean fragile;

	/// Вес
	@Column(nullable = false)
	Float weight;

	/// Размеры
	@Embedded
	Dimension dimension;
}
