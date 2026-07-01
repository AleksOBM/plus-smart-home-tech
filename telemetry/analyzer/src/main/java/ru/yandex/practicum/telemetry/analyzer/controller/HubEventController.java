package ru.yandex.practicum.telemetry.analyzer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.analyzer.mapper.ScenarioMapper;
import ru.yandex.practicum.telemetry.analyzer.mapper.SensorMapper;
import ru.yandex.practicum.telemetry.analyzer.service.HubEventService;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventController {

	private final HubEventService hubEventService;

	public void updateHubData(@NonNull HubEventAvro hubEvent) {
		var hubId = hubEvent.getHubId();

		switch (hubEvent.getPayload()) {

			case DeviceAddedEventAvro event -> hubEventService
					.saveSensor(SensorMapper.toEntity(event, hubId));

			case DeviceRemovedEventAvro event -> hubEventService
					.removeSensor(SensorMapper.toEntity(event, hubId));

			case ScenarioAddedEventAvro event -> hubEventService
					.saveScenario(ScenarioMapper.toScenario(event, hubId));

			case ScenarioRemovedEventAvro event -> hubEventService
					.removeScenario(hubId, event.getName());

			default -> throw new IllegalStateException("Неопознанный тип события: " + hubEvent);
		}
	}
}
