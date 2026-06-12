package ru.yandex.practicum.telemetry.collector.model.sensor.data;

/**
 * Перечисление типов событий датчиков.
 * Определяет различные типы событий,
 * которые могут быть связаны с датчиками.
 */
public enum SensorEventType {
	LIGHT_SENSOR_EVENT,
	MOTION_SENSOR_EVENT,
	TEMPERATURE_SENSOR_EVENT,
	CLIMATE_SENSOR_EVENT,
	SWITCH_SENSOR_EVENT
}
