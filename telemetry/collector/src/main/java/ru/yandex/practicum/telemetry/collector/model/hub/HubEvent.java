package ru.yandex.practicum.telemetry.collector.model.hub;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.telemetry.collector.model.Event;
import ru.yandex.practicum.telemetry.collector.model.hub.data.HubEventType;

@NotNull
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
public abstract class HubEvent extends Event {

	@NotNull
	public abstract HubEventType getType();
}
