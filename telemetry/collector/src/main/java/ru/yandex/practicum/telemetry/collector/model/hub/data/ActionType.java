package ru.yandex.practicum.telemetry.collector.model.hub.data;

/**
 * Перечисление возможных типов
 * действий при срабатывании условия
 * активации сценария.
 */
public enum ActionType {
	ACTIVATE,
	DEACTIVATE,
	INVERSE,
	SET_VALUE
}
