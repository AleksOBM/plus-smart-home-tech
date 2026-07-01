package ru.yandex.practicum.telemetry.analyzer.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.telemetry.analyzer.model.Action;
import ru.yandex.practicum.telemetry.analyzer.model.ActionType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class ActionMapper {

	public Action toEntity(@NonNull DeviceActionAvro avro) {
		return Action.builder()
				.type(ActionType.valueOf(avro.getType().name()))
				.value(avro.getValue())
				.build();
	}

	public Map<String, Action> toMap(@NonNull List<DeviceActionAvro> avroList) {
		return avroList.stream().collect(Collectors.toMap(
				DeviceActionAvro::getSensorId,
				ActionMapper::toEntity));
	}
}
