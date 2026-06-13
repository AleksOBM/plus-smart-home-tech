package ru.yandex.practicum.telemetry.collector.mapper;

import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.ScenarioRemovedEvent;

@UtilityClass
public class HubMapper {
	public HubEventAvro toAvro(@Nonnull HubEvent hubEvent) {
		HubEventAvro.Builder hubEventAvro = HubEventAvro.newBuilder()
				.setHubId(hubEvent.getHubId())
				.setTimestamp(hubEvent.getTimestamp());

		switch (hubEvent) {
			case DeviceAddedEvent event -> hubEventAvro.setPayload(toDeviceAddedEvent(event));
			case DeviceRemovedEvent event -> hubEventAvro.setPayload(toDeviceRemovedEvent(event));
			case ScenarioAddedEvent event -> hubEventAvro.setPayload(toScenarioAddedEvent(event));
			case ScenarioRemovedEvent event -> hubEventAvro.setPayload(toScenarioRemovedEvent(event));
			default -> throw new IllegalStateException("Unknown hub event: " + hubEvent);
		}

		return hubEventAvro.build();
	}

	private static DeviceAddedEventAvro toDeviceAddedEvent(@Nonnull DeviceAddedEvent event) {
		return DeviceAddedEventAvro.newBuilder()
				.setId(event.getId())
				.setDeviceType(DeviceTypeAvro.valueOf(event.getDeviceType().name()))
				.build();
	}

	private static DeviceRemovedEventAvro toDeviceRemovedEvent(@Nonnull DeviceRemovedEvent event) {
		return DeviceRemovedEventAvro.newBuilder()
				.setId(event.getId())
				.setDeviceType(DeviceTypeAvro.valueOf(event.getDeviceType().name()))
				.build();
	}

	private static ScenarioAddedEventAvro toScenarioAddedEvent(@Nonnull ScenarioAddedEvent event) {
		return ScenarioAddedEventAvro.newBuilder()
				.setName(event.getName())
				.setConditions(event.getConditions().stream().map(condition ->
								ScenarioConditionAvro.newBuilder()
										.setSensorId(condition.getSensorId())
										.setType(ConditionTypeAvro.valueOf(condition.getType().name()))
										.setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
										.setValue(condition.getValue())
										.build()
						).toList()
				)
				.setActions(event.getActions().stream().map(action ->
								DeviceActionAvro.newBuilder()
										.setSensorId(action.getSensorId())
										.setType(ActionTypeAvro.valueOf(action.getType().name()))
										.setValue(action.getValue())
										.build()
						).toList()
				)
				.build();
	}

	private static ScenarioRemovedEventAvro toScenarioRemovedEvent(@Nonnull ScenarioRemovedEvent event) {
		return ScenarioRemovedEventAvro.newBuilder()
				.setName(event.getName())
				.build();
	}
}
