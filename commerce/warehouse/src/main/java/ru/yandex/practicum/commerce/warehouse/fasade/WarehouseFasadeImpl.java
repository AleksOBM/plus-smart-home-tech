package ru.yandex.practicum.commerce.warehouse.fasade;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.warehouse.service.DatabaseWarehouseService;
import ru.yandex.practicum.commerce.warehouse.service.InteractionWarehouseService;

@Service
@RequiredArgsConstructor
public class WarehouseFasadeImpl implements WarehouseFasade {

	@Delegate
	private final DatabaseWarehouseService databaseWarehouseService;

	@Delegate
	private final InteractionWarehouseService interactionWarehouseService;
}
