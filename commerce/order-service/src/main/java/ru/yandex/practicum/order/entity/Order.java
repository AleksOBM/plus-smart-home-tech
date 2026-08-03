package ru.yandex.practicum.order.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	String customerName;

	@Column(nullable = false)
	String customerEmail;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	OrderStatus status = OrderStatus.CREATED;

	@Builder.Default
	@Column(nullable = false, precision = 10, scale = 2)
	BigDecimal totalPrice = BigDecimal.ZERO;

	@Column
	String statusDetails;

	@Builder.Default
	@Column(nullable = false)
	LocalDateTime createdAt = LocalDateTime.now();

	@Builder.Default
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
			orphanRemoval = true, fetch = FetchType.LAZY
	)
	List<OrderItem> items = new ArrayList<>();
}