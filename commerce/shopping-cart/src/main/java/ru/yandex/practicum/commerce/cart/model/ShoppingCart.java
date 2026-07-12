package ru.yandex.practicum.commerce.cart.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/// Корзина товаров в онлайн магазине
@Entity
@Getter
@Setter
@Builder
@Table(name = "shopping_carts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCart {

	/// Идентификатор корзины в БД
	@Id
	@Column(name = "id", nullable = false)
	UUID shoppingCartId;

	@Column(nullable = false, unique = true)
	String userId;

	@Builder.Default
	@Column(name = "is_open", nullable = false)
	boolean open = true;

	/// Отображение идентификатора товара на отобранное количество
	@Builder.Default
	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "product_count", nullable = false)
	@MapKeyColumn(name = "product_id")
	@CollectionTable(
			name = "products_in_cart",
			joinColumns = @JoinColumn(name = "shopping_cart_id")
	)
	Map<UUID, Integer> products = new HashMap<>();

}
