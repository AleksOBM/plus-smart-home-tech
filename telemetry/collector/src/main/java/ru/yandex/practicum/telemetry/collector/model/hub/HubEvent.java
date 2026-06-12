package ru.yandex.practicum.telemetry.collector.model.hub;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.collector.model.Event;
import ru.yandex.practicum.telemetry.collector.model.hub.data.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.hub.events.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.events.ScenarioRemovedEvent;

@NotNull
@Getter
@Setter
@ToString(callSuper = true)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		property = "type",
		defaultImpl = HubEventTypeAvro.class
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DEVICE_ADDED"),
		@JsonSubTypes.Type(value = DeviceRemovedEvent.class, name = "DEVICE_REMOVED"),
		@JsonSubTypes.Type(value = ScenarioAddedEvent.class, name = "SCENARIO_ADDED"),
		@JsonSubTypes.Type(value = ScenarioRemovedEvent.class, name = "SCENARIO_REMOVED")
})
public abstract class HubEvent extends Event {

	@NotNull
	public abstract HubEventType getType();
}
