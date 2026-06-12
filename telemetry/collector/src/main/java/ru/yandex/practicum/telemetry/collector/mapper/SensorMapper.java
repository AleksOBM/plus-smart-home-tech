package ru.yandex.practicum.telemetry.collector.mapper;

import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.events.*;

@UtilityClass
public class SensorMapper {
	public SensorEventAvro toAvro(@Nonnull SensorEvent sensorEvent) {
		SensorEventAvro.Builder sensorEventAvro = SensorEventAvro.newBuilder()
				.setId(sensorEvent.getId())
				.setHubId(sensorEvent.getHubId())
				.setTimestamp(sensorEvent.getTimestamp());

		switch (sensorEvent) {
			case LightSensorEvent event -> sensorEventAvro.setPayload(toLightSensorAvro(event));
			case MotionSensorEvent event -> sensorEventAvro.setPayload(toMotionSensorAvro(event));
			case TemperatureSensorEvent event -> sensorEventAvro.setPayload(toTemperatureSensorAvro(event));
			case ClimateSensorEvent event -> sensorEventAvro.setPayload(toClimateSensorAvro(event));
			case SwitchSensorEvent event -> sensorEventAvro.setPayload(toSwitchSensorAvro(event));
			default -> throw new IllegalArgumentException("Unknown sensor event " + sensorEvent);
		}

		return sensorEventAvro.build();
	}

	private LightSensorEventAvro toLightSensorAvro(@Nonnull LightSensorEvent sensorEvent) {
		return LightSensorEventAvro.newBuilder()
				.setLinkQuality(sensorEvent.getLinkQuality())
				.setLuminosity(sensorEvent.getLuminosity())
				.build();
	}

	private MotionSensorEventAvro toMotionSensorAvro(@Nonnull MotionSensorEvent sensorEvent) {
		return MotionSensorEventAvro.newBuilder()
				.setLinkQuality(sensorEvent.getLinkQuality())
				.setMotion(sensorEvent.getMotion())
				.setVoltage(sensorEvent.getVoltage())
				.build();
	}

	private TemperatureSensorEventAvro toTemperatureSensorAvro(@Nonnull TemperatureSensorEvent sensorEvent) {
		return TemperatureSensorEventAvro.newBuilder()
				.setTemperatureC(sensorEvent.getTemperatureC())
				.setTemperatureF(sensorEvent.getTemperatureF())
				.build();
	}

	private ClimateSensorEventAvro toClimateSensorAvro(@Nonnull ClimateSensorEvent sensorEvent) {
		return ClimateSensorEventAvro.newBuilder()
				.setTemperatureC(sensorEvent.getTemperatureC())
				.setCo2Level(sensorEvent.getCo2Level())
				.setHumidity(sensorEvent.getHumidity())
				.build();
	}

	private SwitchSensorEventAvro toSwitchSensorAvro(@Nonnull SwitchSensorEvent sensorEvent) {
		return SwitchSensorEventAvro.newBuilder()
				.setState(sensorEvent.getState())
				.build();
	}
}
