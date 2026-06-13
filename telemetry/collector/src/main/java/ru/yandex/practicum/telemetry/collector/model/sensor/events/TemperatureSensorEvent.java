package ru.yandex.practicum.telemetry.collector.model.sensor.events;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.data.SensorEventType;

@Getter
@Setter
public class TemperatureSensorEvent extends SensorEvent {

	/**
	 * Уровень температуры по шкале Цельсия.
	 */
	private Integer temperatureC;

	/**
	 * Температура в градусах Фаренгейта.
	 */
	private Integer temperatureF;

	@Override
	public SensorEventType getType() {
		return SensorEventType.TEMPERATURE_SENSOR_EVENT;
	}
}
