package ru.yandex.practicum.order.service;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.order.dto.CreateOrderRequest;
import ru.yandex.practicum.order.dto.OrderDto;

import java.util.List;

@Transactional
public interface OrderService {

	@Transactional(readOnly = true)
	List<OrderDto> getAllOrders();

	@Transactional(readOnly = true)
	OrderDto getOrderById(Long id);

	List<OrderDto> getOrdersByEmail(String email);

	OrderDto createOrder(CreateOrderRequest request);
}
