package ru.yandex.practicum.telemetry.collector.mapper;

import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.events.*;
import ru.yandex.practicum.telemetry.collector.util.CollectorUtils;

@UtilityClass
public class SensorMapper {

	public SensorEventAvro toAvro(@Nonnull SensorEvent sensorEvent) {
		SensorEventAvro.Builder sensorEventAvro = SensorEventAvro.newBuilder()
				.setId(sensorEvent.getId())
				.setHubId(sensorEvent.getHubId())
				.setTimestamp(sensorEvent.getTimestamp());

		switch (sensorEvent) {
			case ClimateSensorEvent event -> sensorEventAvro.setPayload(toClimateSensorAvro(event));
			case LightSensorEvent event -> sensorEventAvro.setPayload(toLightSensorAvro(event));
			case MotionSensorEvent event -> sensorEventAvro.setPayload(toMotionSensorAvro(event));
			case SwitchSensorEvent event -> sensorEventAvro.setPayload(toSwitchSensorAvro(event));
			case TemperatureSensorEvent event -> sensorEventAvro.setPayload(toTemperatureSensorAvro(event));
			default -> throw new IllegalArgumentException("Unknown sensor event " + sensorEvent);
		}

		return sensorEventAvro.build();
	}

	private LightSensorAvro toLightSensorAvro(@Nonnull LightSensorEvent sensorEvent) {
		return LightSensorAvro.newBuilder()
				.setLinkQuality(sensorEvent.getLinkQuality())
				.setLuminosity(sensorEvent.getLuminosity())
				.build();
	}

	private MotionSensorAvro toMotionSensorAvro(@Nonnull MotionSensorEvent sensorEvent) {
		return MotionSensorAvro.newBuilder()
				.setLinkQuality(sensorEvent.getLinkQuality())
				.setMotion(sensorEvent.getMotion())
				.setVoltage(sensorEvent.getVoltage())
				.build();
	}

	private TemperatureSensorAvro toTemperatureSensorAvro(@Nonnull TemperatureSensorEvent sensorEvent) {
		return TemperatureSensorAvro.newBuilder()
				.setTemperatureC(sensorEvent.getTemperatureC())
				.setTemperatureF(sensorEvent.getTemperatureF())
				.build();
	}

	private ClimateSensorAvro toClimateSensorAvro(@Nonnull ClimateSensorEvent sensorEvent) {
		return ClimateSensorAvro.newBuilder()
				.setTemperatureC(sensorEvent.getTemperatureC())
				.setCo2Level(sensorEvent.getCo2Level())
				.setHumidity(sensorEvent.getHumidity())
				.build();
	}

	private SwitchSensorAvro toSwitchSensorAvro(@Nonnull SwitchSensorEvent sensorEvent) {
		return SwitchSensorAvro.newBuilder()
				.setState(sensorEvent.getState())
				.build();
	}

	public SensorEvent toEntity(@NonNull SensorEventProto eventProto) {
		return switch (eventProto.getPayloadCase()) {
			case CLIMATE_SENSOR -> toClimateSensor(eventProto);
			case LIGHT_SENSOR -> toLightSensor(eventProto);
			case MOTION_SENSOR -> toMotionSensor(eventProto);
			case SWITCH_SENSOR -> toSwitchSensor(eventProto);
			case TEMPERATURE_SENSOR -> toTemperatureSensor(eventProto);
			default -> throw new IllegalArgumentException("Unknown sensor event " + eventProto.getPayloadCase());
		};
	}

	private ClimateSensorEvent toClimateSensor(@NonNull SensorEventProto eventProto) {
		ClimateSensorProto climateSensor = eventProto.getClimateSensor();
		return ClimateSensorEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.id(eventProto.getId())
				.temperatureC(climateSensor.getTemperatureC())
				.humidity(climateSensor.getHumidity())
				.co2Level(climateSensor.getCo2Level())
				.build();
	}

	private LightSensorEvent toLightSensor(@NonNull SensorEventProto eventProto) {
		LightSensorProto lightSensor = eventProto.getLightSensor();
		return LightSensorEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.id(eventProto.getId())
				.linkQuality(lightSensor.getLinkQuality())
				.luminosity(lightSensor.getLuminosity())
				.build();
	}

	private MotionSensorEvent toMotionSensor(@NonNull SensorEventProto eventProto) {
		MotionSensorProto motionSensor = eventProto.getMotionSensor();
		return MotionSensorEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.id(eventProto.getId())
				.linkQuality(motionSensor.getLinkQuality())
				.motion(motionSensor.getMotion())
				.voltage(motionSensor.getVoltage())
				.build();
	}

	private SwitchSensorEvent toSwitchSensor(@NonNull SensorEventProto eventProto) {
		SwitchSensorProto switchSensor = eventProto.getSwitchSensor();
		return SwitchSensorEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.id(eventProto.getId())
				.state(switchSensor.getState())
				.build();
	}

	private TemperatureSensorEvent toTemperatureSensor(@NonNull SensorEventProto eventProto) {
		TemperatureSensorProto temperatureSensor = eventProto.getTemperatureSensor();
		return TemperatureSensorEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.id(eventProto.getId())
				.temperatureC(temperatureSensor.getTemperatureC())
				.temperatureF(temperatureSensor.getTemperatureF())
				.build();
	}

}
