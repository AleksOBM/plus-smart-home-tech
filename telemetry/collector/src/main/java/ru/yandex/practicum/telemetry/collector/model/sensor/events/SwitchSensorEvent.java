package ru.yandex.practicum.telemetry.collector.model.sensor.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.data.SensorEventType;

@Getter
@Setter
@SuperBuilder
public class SwitchSensorEvent extends SensorEvent {

	/**
	 * Текущее состояние переключателя.
	 */
	private Boolean state;

	@Override
	public SensorEventType getType() {
		return SensorEventType.SWITCH_SENSOR_EVENT;
	}
}
