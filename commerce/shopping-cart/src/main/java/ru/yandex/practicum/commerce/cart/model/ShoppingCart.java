package ru.yandex.practicum.commerce.cart.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/// Корзина товаров в онлайн магазине
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_carts")
public class ShoppingCart {

	/// Идентификатор корзины в БД
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID shoppingCartId;

	/// Имя пользователя-владельца корзины
	@Column(nullable = false)
	private String username;

	/// Текущий статус корзины. Закрытая корзина больше не изменяется
	@Column(name = "is_open", nullable = false)
	@Builder.Default
	private boolean open = true;

	/// Отображение идентификатора товара на отобранное количество
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "products_in_cart",
			joinColumns = @JoinColumn(name = "shopping_cart_id")
	)
	@MapKeyColumn(name = "product_id")
	@Column(name = "product_count")
	@Builder.Default
	private Map<UUID, Integer> products = new HashMap<>();
}
