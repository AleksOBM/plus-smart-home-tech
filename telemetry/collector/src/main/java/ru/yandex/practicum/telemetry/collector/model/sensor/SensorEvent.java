package ru.yandex.practicum.telemetry.collector.model.sensor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.telemetry.collector.model.Event;
import ru.yandex.practicum.telemetry.collector.model.sensor.data.SensorEventType;

@NotNull
@Getter
@Setter
@SuperBuilder
public abstract class SensorEvent extends Event {

	/**
	 * Идентификатор события датчика.
	 */
	@NotBlank
	private String id;

	/**
	 * Тип события датчика
	 */
	@NotNull
	public abstract SensorEventType getType();

}
