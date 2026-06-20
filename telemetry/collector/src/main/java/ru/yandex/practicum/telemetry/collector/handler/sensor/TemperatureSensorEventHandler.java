package ru.yandex.practicum.telemetry.collector.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.telemetry.collector.handler.SensorEventHandler;
import ru.yandex.practicum.telemetry.collector.mapper.SensorMapper;
import ru.yandex.practicum.telemetry.collector.service.EventService;

@Component
@RequiredArgsConstructor
public class TemperatureSensorEventHandler implements SensorEventHandler {

	private final EventService eventService;

	@Override
	public SensorEventProto.PayloadCase getMessageType() {
		return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR;
	}

	@Override
	public void handle(SensorEventProto event) {
		eventService.collectSensorsEvent(SensorMapper.toEntity(event));
	}
}
