package ru.yandex.practicum.telemetry.collector.model.sensor.events;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.data.SensorEventType;

@Getter
@Setter
public class MotionSensorEvent extends SensorEvent {

	/** Качество связи. */
	private Integer linkQuality;

	/** Наличие/отсутствие движения. */
	private Boolean motion;

	/** Напряжение. */
	private Integer voltage;

	@Override
	public SensorEventType getType() {
		return SensorEventType.MOTION_SENSOR_EVENT;
	}
}
