package ru.yandex.practicum.telemetry.analyzer.model;

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
