package ru.yandex.practicum.telemetry.collector.model.sensor.events;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.data.SensorEventType;

@Getter
@Setter
public class LightSensorEvent extends SensorEvent {

	/**
	 * Качество связи.
	 */
	private Integer linkQuality;

	/**
	 * Уровень освещенности.
	 */
	private Integer luminosity;

	@Override
	public SensorEventType getType() {
		return SensorEventType.LIGHT_SENSOR_EVENT;
	}
}
