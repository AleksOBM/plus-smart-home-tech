package ru.yandex.practicum.inventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.inventory.dto.InventoryDto;
import ru.yandex.practicum.inventory.dto.ReserveRequest;
import ru.yandex.practicum.inventory.dto.ReserveResponse;
import ru.yandex.practicum.inventory.dto.UpdateInventoryRequest;
import ru.yandex.practicum.inventory.service.InventoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	/**
	 * Получить все остатки
	 * @return List.of({@link InventoryDto}) - возвращает остатки по всем товарам
	 */
	@GetMapping
	public List<InventoryDto> getFullInventory() {
		log.info("GET /api/inventory");
		return inventoryService.getFullInventory();
	}

	/**
	 * Остатки по товару
	 *
	 * @param productId ID товара
	 * @return {@link InventoryDto} - возвращает остатки для конкретного товара
	 */
	@GetMapping("/{productId}")
	public InventoryDto getInventoryRecord(@PathVariable Long productId) {
		log.info("GET /api/inventory/{}", productId);
		return inventoryService.getInventoryRecord(productId);
	}

	/**
	 * Обновить остатки
	 * <br/>
	 * Обновляет количество товара на складе. Запись должна уже существовать.
	 *
	 * @param request запрос на установку количества товара на складе
	 * @return {@link InventoryDto} - возвращает остатки по всем товарам
	 */
	@PutMapping
	public InventoryDto updateInventoryRecord(@Valid @RequestBody UpdateInventoryRequest request) {
		log.info("PUT /api/inventory\nbody:{}", request);
		return inventoryService.updateInventoryRecord(request);
	}

	/**
	 * Создать запись об остатках
	 * <br/>
	 * Создаёт запись об остатках для нового товара. Если запись уже существует - ошибка.
	 *
	 * @param request запрос на установку количества товара на складе
	 * @return {@link InventoryDto} - возвращает остатки по всем товарам
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InventoryDto createInventoryRecord(@Valid @RequestBody UpdateInventoryRequest request) {
		log.info("POST /api/inventory\nbody:{}", request);
		return inventoryService.createInventoryRecord(request);
	}

	/**
	 * Зарезервировать товар
	 * <br/>
	 * Резервирует указанное количество товара.
	 * При конфликте (параллельные запросы на резерв одного и того же товара) возвращает 409 Conflict.
	 *
	 * @param request запрос на резервирование товара
	 * @return {@link ReserveResponse} - результат резервирования товара
	 */
	@PostMapping("/reserve")
	public ReserveResponse reserveProduct(@Valid @RequestBody ReserveRequest request) {
		log.info("POST /api/inventory/reserve\nbody:{}", request);
		return inventoryService.reserveProduct(request);
	}

	/**
	 * Отменить резервирование товара
	 * @param request запрос на отмену резервирования товара
	 * @return {@link ReserveResponse} - результат отмены резервирования товара
	 */
	@PostMapping("/release")
	public ReserveResponse releaseProduct(@Valid @RequestBody ReserveRequest request) {
		log.info("POST /api/inventory/release\nbody:{}", request);
		return inventoryService.releaseProduct(request);
	}
}
