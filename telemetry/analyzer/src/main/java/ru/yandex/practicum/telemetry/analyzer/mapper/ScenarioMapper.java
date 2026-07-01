package ru.yandex.practicum.telemetry.analyzer.mapper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.telemetry.analyzer.model.Scenario;

@UtilityClass
public class ScenarioMapper {

	public Scenario toScenario(@NonNull ScenarioAddedEventAvro avro, String hubId) {
		return Scenario.builder()
				.hubId(hubId)
				.name(avro.getName())
				.conditions(ConditionMapper.toMap(avro.getConditions()))
				.actions(ActionMapper.toMap(avro.getActions()))
				.build();
	}
}
