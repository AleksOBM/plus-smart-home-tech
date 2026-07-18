package ru.yandex.practicum.commerce.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.commerce.warehouse.model.ProductQuantityProjection;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<WarehouseProduct, UUID> {

	Integer getQuantityFreeByProductId(UUID id);

	@Query("""
			    select new ru.yandex.practicum.commerce.warehouse.model.ProductQuantityProjection(
			        p.productId,
			        p.quantityAll - p.quantityBooked
			    )
			    from WarehouseProduct p
			    where p.productId in :productIds
			""")
	List<ProductQuantityProjection> findFreeQuantities(Set<UUID> productIds);
}
