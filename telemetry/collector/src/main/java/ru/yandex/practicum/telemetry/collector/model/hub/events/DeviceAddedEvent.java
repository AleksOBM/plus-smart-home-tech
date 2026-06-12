package ru.yandex.practicum.telemetry.collector.model.hub.events;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.data.DeviceType;
import ru.yandex.practicum.telemetry.collector.model.hub.data.HubEventType;

@Getter
@Setter
public class DeviceAddedEvent extends HubEvent {

	/** Идентификатор устройства. */
	private String id;

	/** Тип устройства. */
	private DeviceType deviceType;

	@Override
	public HubEventType getType() {
		return HubEventType.DEVICE_ADDED;
	}
}
