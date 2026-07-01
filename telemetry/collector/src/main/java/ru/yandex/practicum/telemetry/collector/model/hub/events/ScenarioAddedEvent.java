package ru.yandex.practicum.telemetry.collector.model.hub.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.data.DeviceAction;
import ru.yandex.practicum.telemetry.collector.model.hub.data.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.hub.data.ScenarioCondition;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ScenarioAddedEvent extends HubEvent {

	/**
	 * Название добавленного сценария.
	 * Должно содержать не менее 3 символов.
	 */
	private String name;

	/**
	 * Список условий, которые связаны
	 * со сценарием. Не может быть пустым.
	 */
	private List<ScenarioCondition> conditions;

	/**
	 * Список действий, которые должны
	 * быть выполнены в рамках сценария.
	 * Не может быть пустым.
	 */
	private List<DeviceAction> actions;

	@Override
	public HubEventType getType() {
		return HubEventType.SCENARIO_ADDED;
	}
}
