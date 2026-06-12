package ru.yandex.practicum.telemetry.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.mapper.HubMapper;
import ru.yandex.practicum.telemetry.collector.mapper.SensorMapper;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.producer.SmartAvroProducer;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	@Value("${sht.telemetry.topics.sensors}")
	private String sensorsTopic;

	@Value("${sht.telemetry.topics.hubs}")
	private String hubsTopic;

	private final SmartAvroProducer producer;

	@Override
	public void collectSensorsEvent(SensorEvent sensorEvent) {
		SensorEventAvro avro = SensorMapper.toAvro(sensorEvent);
		CompletableFuture<RecordMetadata> result = producer.sendMessage(sensorsTopic, sensorEvent, avro);
		result.whenComplete((record, throwable) -> {
			if (throwable != null) {
				log.error("Ошибка отправки события сенсора {}:\n {}",
						sensorEvent.getType(), throwable.getMessage(), throwable);
			} else {
				log.info("Событие сенсора отправлено:\n topic={}, key={}, partition={}, offset={}",
						sensorsTopic, sensorEvent.getHubId(), record.partition(), record.offset());
			}
		});
	}

	@Override
	public void collectHubEvent(HubEvent hubEvent) {
		HubEventAvro avro = HubMapper.toAvro(hubEvent);
		CompletableFuture<RecordMetadata> result = producer.sendMessage(hubsTopic, hubEvent, avro);
		result.whenComplete((record, throwable) -> {
			if (throwable != null) {
				log.error("Ошибка отправки события хаба {}:\n {}",
						hubEvent.getType(), throwable.getMessage(), throwable);
			} else {
				log.info("Событие хаба отправлено:\n topic={}, key={}, partition={}, offset={}",
						hubsTopic, hubEvent.getHubId(), record.partition(), record.offset());
			}
		});
	}
}
