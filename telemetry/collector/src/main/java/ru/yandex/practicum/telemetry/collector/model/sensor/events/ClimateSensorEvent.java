package ru.yandex.practicum.telemetry.collector.model.sensor.events;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.data.SensorEventType;

@Getter
@Setter
public class ClimateSensorEvent extends SensorEvent {

	/**
	 * Уровень температуры по шкале Цельсия.
	 */
	private Integer temperatureC;

	/**
	 * Влажность.
	 */
	private Integer humidity;

	/**
	 * Уровень CO2.
	 */
	private Integer co2Level;

	@Override
	public SensorEventType getType() {
		return SensorEventType.CLIMATE_SENSOR_EVENT;
	}
}
