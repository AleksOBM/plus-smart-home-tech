package ru.yandex.practicum.telemetry.analyzer.service;

import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;
import ru.yandex.practicum.telemetry.analyzer.model.Scenario;
import ru.yandex.practicum.telemetry.analyzer.model.Sensor;

@Transactional
public interface HubEventService {

	void saveSensor(@NonNull Sensor sensor);

	void removeSensor(@NonNull Sensor sensor);

	void saveScenario(@NonNull Scenario scenario);

	void removeScenario(String hubId, String name);
}
