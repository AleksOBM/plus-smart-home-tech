package ru.yandex.practicum.commerce.store.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.commerce.interaction.enums.ProductCategory;
import ru.yandex.practicum.commerce.interaction.enums.ProductState;
import ru.yandex.practicum.commerce.interaction.enums.QuantityState;

import java.util.UUID;


@Getter
@Entity
@NoArgsConstructor(force = true)
@SuperBuilder(toBuilder = true)
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

	/// Идентификатор товара в БД
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID productId;

	/// Наименование товара
	@Column(nullable = false)
	String productName;

	/// Описание товара
	@Column(nullable = false)
	String description;

	/// Ссылка на картинку во внешнем хранилище или SVG
	@Column
	String imageSrc;

	/// Статус, перечисляющий состояние остатка как свойства товара
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	QuantityState quantityState;

	/// Статус товара
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	ProductState productState;

	/// Категория товара
	@Column
	@Enumerated(EnumType.STRING)
	ProductCategory productCategory;

	/// Цена товара
	@Column(nullable = false)
	Float price;
}
