package ru.yandex.practicum.telemetry.collector.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.service.EventService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

	private final EventService eventService;
	private final ObjectMapper objectMapper;

	@PostMapping("/sensors")
	public void collectSensorsEvent(@Valid @RequestBody SensorEvent sensorEvent) throws JsonProcessingException {
		log.info("Получено новое событие сенсора:\n{}", objectMapper.writeValueAsString(sensorEvent));
		eventService.collectSensorsEvent(sensorEvent);
	}

	@PostMapping("/hubs")
	public void collectHubEvent(@Valid @RequestBody HubEvent hubEvent) throws JsonProcessingException {
		log.info("Получено новое событие хаба:\n{}", objectMapper.writeValueAsString(hubEvent));
		eventService.collectHubEvent(hubEvent);
	}
}
