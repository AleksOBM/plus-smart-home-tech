package ru.yandex.practicum.telemetry.analyzer.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.telemetry.analyzer.model.Condition;
import ru.yandex.practicum.telemetry.analyzer.model.ConditionOperation;
import ru.yandex.practicum.telemetry.analyzer.model.ConditionType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class ConditionMapper {

	public Condition toEntity(@NonNull ScenarioConditionAvro avro) {
		return Condition.builder()
				.type(ConditionType.valueOf(avro.getType().name()))
				.operation(ConditionOperation.valueOf(avro.getOperation().name()))
				.value(avro.getValue())
				.build();
	}

	public Map<String, Condition> toMap(@NonNull List<ScenarioConditionAvro> avroList) {
		return avroList.stream().collect(Collectors.toMap(
				ScenarioConditionAvro::getSensorId,
				ConditionMapper::toEntity));
	}
}
