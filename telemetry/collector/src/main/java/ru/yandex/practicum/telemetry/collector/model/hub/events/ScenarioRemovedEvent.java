package ru.yandex.practicum.telemetry.collector.model.hub.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.data.HubEventType;


@Getter
@Setter
@SuperBuilder
public class ScenarioRemovedEvent extends HubEvent {

	/**
	 * Название удаленного сценария.
	 * Должно содержать не менее 3 символов.
	 */
	String name;

	@Override
	public HubEventType getType() {
		return HubEventType.SCENARIO_REMOVED;
	}
}
