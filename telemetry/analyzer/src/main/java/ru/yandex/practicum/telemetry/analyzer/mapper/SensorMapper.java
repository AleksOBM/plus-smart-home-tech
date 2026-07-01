package ru.yandex.practicum.telemetry.analyzer.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.telemetry.analyzer.model.Sensor;

@UtilityClass
public class SensorMapper {

	public Sensor toEntity(@NonNull DeviceAddedEventAvro avro, String hubId) {
		return Sensor.builder()
				.id(avro.getId())
				.hubId(hubId)
				.build();
	}

	public Sensor toEntity(@NonNull DeviceRemovedEventAvro avro, String hubId) {
		return Sensor.builder()
				.id(avro.getId())
				.hubId(hubId)
				.build();
	}
}
