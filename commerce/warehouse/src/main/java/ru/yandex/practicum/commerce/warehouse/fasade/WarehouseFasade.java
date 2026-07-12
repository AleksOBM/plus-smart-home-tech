package ru.yandex.practicum.commerce.warehouse.fasade;

import ru.yandex.practicum.commerce.warehouse.service.DatabaseWarehouseService;
import ru.yandex.practicum.commerce.warehouse.service.InteractionWarehouseService;

public interface WarehouseFasade extends
		DatabaseWarehouseService, InteractionWarehouseService {
}
