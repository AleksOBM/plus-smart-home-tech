package ru.yandex.practicum.telemetry.collector.mapper;

import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.data.*;
import ru.yandex.practicum.telemetry.collector.model.hub.events.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.ScenarioRemovedEvent;
import ru.yandex.practicum.telemetry.collector.util.CollectorUtils;

@UtilityClass
public class HubMapper {
	public HubEventAvro toAvro(@Nonnull HubEvent hubEvent) {
		HubEventAvro.Builder hubEventAvro = HubEventAvro.newBuilder()
				.setHubId(hubEvent.getHubId())
				.setTimestamp(hubEvent.getTimestamp());

		switch (hubEvent) {
			case DeviceAddedEvent event -> hubEventAvro.setPayload(toDeviceAddedEventAvro(event));
			case DeviceRemovedEvent event -> hubEventAvro.setPayload(toDeviceRemovedEventAvro(event));
			case ScenarioAddedEvent event -> hubEventAvro.setPayload(toScenarioAddedEventAvro(event));
			case ScenarioRemovedEvent event -> hubEventAvro.setPayload(toScenarioRemovedEventAvro(event));
			default -> throw new IllegalStateException("Unknown hub event: " + hubEvent);
		}

		return hubEventAvro.build();
	}

	private DeviceAddedEventAvro toDeviceAddedEventAvro(@Nonnull DeviceAddedEvent event) {
		return DeviceAddedEventAvro.newBuilder()
				.setId(event.getId())
				.setDeviceType(DeviceTypeAvro.valueOf(event.getDeviceType().name()))
				.build();
	}

	private DeviceRemovedEventAvro toDeviceRemovedEventAvro(@Nonnull DeviceRemovedEvent event) {
		return DeviceRemovedEventAvro.newBuilder()
				.setId(event.getId())
				.setDeviceType(DeviceTypeAvro.valueOf(event.getDeviceType().name()))
				.build();
	}

	private ScenarioAddedEventAvro toScenarioAddedEventAvro(@Nonnull ScenarioAddedEvent event) {
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

	private ScenarioRemovedEventAvro toScenarioRemovedEventAvro(@Nonnull ScenarioRemovedEvent event) {
		return ScenarioRemovedEventAvro.newBuilder()
				.setName(event.getName())
				.build();
	}

	public HubEvent toEntity(@Nonnull HubEventProto eventProto) {
		return switch (eventProto.getPayloadCase()) {
			case DEVICE_ADDED -> toDeviceAddedEvent(eventProto);
			case DEVICE_REMOVED -> toDeviceRemovedEvent(eventProto);
			case SCENARIO_ADDED -> toScenarioAddedEvent(eventProto);
			case SCENARIO_REMOVED -> toScenarioRemovedEvent(eventProto);
			default -> throw new IllegalStateException("Unknown hub event: " + eventProto);
		};
	}

	private static DeviceAddedEvent toDeviceAddedEvent(@NonNull HubEventProto eventProto) {
		DeviceAddedEventProto proto = eventProto.getDeviceAdded();
		return DeviceAddedEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.id(proto.getId())
				.deviceType(DeviceType.valueOf(proto.getDeviceType().name()))
				.build();
	}

	private static DeviceRemovedEvent toDeviceRemovedEvent(@NonNull HubEventProto eventProto) {
		DeviceRemovedEventProto proto = eventProto.getDeviceRemoved();
		return DeviceRemovedEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.id(proto.getId())
				.deviceType(DeviceType.valueOf(proto.getDeviceType().name()))
				.build();
	}

	private static ScenarioAddedEvent toScenarioAddedEvent(@NonNull HubEventProto eventProto) {
		ScenarioAddedEventProto proto = eventProto.getScenarioAdded();
		return ScenarioAddedEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.name(proto.getName())
				.conditions(proto.getConditionsList().stream().map(conditionProto ->
								ScenarioCondition.builder()
										.sensorId(conditionProto.getSensorId())
										.type(ConditionType.valueOf(conditionProto.getType().name()))
										.operation(ConditionOperation.valueOf(conditionProto.getOperation().name()))
										.value(conditionProto.hasIntValue() ? conditionProto.getIntValue() :
												(conditionProto.getBoolValue() ? 1 : 0)
										)
										.build()
						).toList()
				)
				.actions(proto.getActionsList().stream().map(deviceActionProto ->
								DeviceAction.builder()
										.sensorId(deviceActionProto.getSensorId())
										.type(ActionType.valueOf(deviceActionProto.getType().name()))
										.value(deviceActionProto.getValue())
										.build()

						).toList()
				)
				.build();
	}

	private static ScenarioRemovedEvent toScenarioRemovedEvent(@NonNull HubEventProto eventProto) {
		ScenarioRemovedEventProto proto = eventProto.getScenarioRemoved();
		return ScenarioRemovedEvent.builder()
				.hubId(eventProto.getHubId())
				.timestamp(CollectorUtils.toInstant(eventProto.getTimestamp()))
				.name(proto.getName())
				.build();
	}
}
