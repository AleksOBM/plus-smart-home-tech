package ru.yandex.practicum.telemetry.collector.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.telemetry.collector.handler.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.mapper.HubMapper;
import ru.yandex.practicum.telemetry.collector.service.EventService;

@Component
@RequiredArgsConstructor
public class DeviceRemovedEventHandler implements HubEventHandler {

	private final EventService eventService;

	@Override
	public HubEventProto.PayloadCase getMessageType() {
		return HubEventProto.PayloadCase.DEVICE_REMOVED;
	}

	@Override
	public void handle(HubEventProto event) {
		eventService.collectHubEvent(HubMapper.toEntity(event));
	}
}
