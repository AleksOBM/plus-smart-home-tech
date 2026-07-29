package ru.yandex.practicum.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false, unique = true)
	Long productId;

	@Builder.Default
	@Column(nullable = false)
	Integer quantity = 0;

	@Builder.Default
	@Column(nullable = false)
	Integer reservedQuantity = 0;

	@Version
	Long version;

	public Integer getAvailableQuantity() {
		return quantity - reservedQuantity;
	}
}