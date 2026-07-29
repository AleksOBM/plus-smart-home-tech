package ru.yandex.practicum.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.order.dto.CreateOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.service.OrderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	/**
	 * Все заказы
	 *
	 * @return List.of({@link OrderDto}) - Возвращает список всех заказов
	 */
	@GetMapping
	public List<OrderDto> getAllOrders() {
		log.info("GET /api/categories");
		return orderService.getAllOrders();
	}

	/**
	 * Получить заказ по ID
	 *
	 * @param id ID заказа
	 * @return {@link OrderDto} - Информация о заказе
	 */
	@GetMapping("/{id}")
	public OrderDto getOrderById(@PathVariable Long id) {
		log.info("GET /api/categories/{}", id);
		return orderService.getOrderById(id);
	}

	/**
	 * Заказы клиента
	 *
	 * @param email Поиск заказов по email клиента
	 * @return List.of({@link OrderDto}) - Возвращает список заказов клиента
	 */
	@GetMapping("/by-email")
	public List<OrderDto> getOrdersByEmail(@RequestParam String email) {
		log.info("GET /api/categories/by-email\nparam:{}", email);
		return orderService.getOrdersByEmail(email);
	}

	/**
	 * Создать заказ
	 * <br/>
	 * Создаёт заказ в локальной базе.
	 * На этом этапе order-service не обращается к product-service и inventory-service.
	 *
	 * @param request Запрос на создание заказа
	 * @return {@link OrderDto} - Информация о заказе
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDto createOrder(@Valid @RequestBody CreateOrderRequest request) {
		log.info("POST /api/categories\nbody:{}", request);
		return orderService.createOrder(request);
	}

}
