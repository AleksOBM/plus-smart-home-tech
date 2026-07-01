package ru.yandex.practicum.telemetry.analyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.telemetry.analyzer.model.Action;
import ru.yandex.practicum.telemetry.analyzer.model.Condition;
import ru.yandex.practicum.telemetry.analyzer.model.Scenario;
import ru.yandex.practicum.telemetry.analyzer.model.Sensor;
import ru.yandex.practicum.telemetry.analyzer.repository.ActionRepository;
import ru.yandex.practicum.telemetry.analyzer.repository.ConditionRepository;
import ru.yandex.practicum.telemetry.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.telemetry.analyzer.repository.SensorRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubEventServiceImpl implements HubEventService {

	private final ActionRepository actionRepository;
	private final ConditionRepository conditionRepository;
	private final ScenarioRepository scenarioRepository;
	private final SensorRepository sensorRepository;

	@Override
	public void saveSensor(@NonNull Sensor sensor) {
		if (sensorRepository.existsById(sensor.getId())) {
			log.warn("Сенсор с ID {} уже существует", sensor.getId());
		}
		log.info("Добавление нового сенсора: {}", sensor);
		sensorRepository.save(sensor);
	}

	@Override
	public void removeSensor(@NonNull Sensor sensor) {
		log.info("Удаление сенсора: {}", sensor);
		sensorRepository.delete(sensor);
	}

	@Override
	public void saveScenario(@NonNull Scenario scenario) {

		Optional<Scenario> oldScenario = scenarioRepository
				.findByHubIdAndName(scenario.getHubId(), scenario.getName());

		if (oldScenario.isPresent()) {
			updateScenario(oldScenario.get(), scenario);
		} else {
			if (!checkSensors(scenario)) {
				log.warn("Один или несколько сенсоров не найдены в этом хабе");
				return;
			}
			log.info("Добавление нового сценария: {}", scenario);
			saveConditions(scenario.getConditions());
			saveActions(scenario.getActions());
			scenarioRepository.save(scenario);
		}
	}

	@Override
	public void removeScenario(String hubId, String name) {
		log.info("Удаление сценария");
		if (hubId == null || hubId.isEmpty() || name == null || name.isEmpty()) {
			throw new IllegalArgumentException("hubId и name не могут быть пустыми");
		}
		Optional<Scenario> scenario = scenarioRepository.findByHubIdAndName(hubId, name);
		if (scenario.isPresent()) {
			log.debug("Удаляем связанные условия и действия");
			conditionRepository.deleteAll(scenario.get().getConditions().values());
			actionRepository.deleteAll(scenario.get().getActions().values());
		}
		scenarioRepository.deleteByHubIdAndName(hubId, name);
	}

	private boolean checkSensors(@NonNull Scenario scenario) {
		log.debug("Проверяем есть ли сенсоры указанные в сценарии");
		var sensorIds = new HashSet<String>();
		sensorIds.addAll(scenario.getConditions().keySet());
		sensorIds.addAll(scenario.getActions().keySet());
		return sensorRepository.existsByIdInAndHubId(sensorIds, scenario.getHubId());
	}

	private void saveConditions(@NonNull Map<String, Condition> conditions) {
		if (conditions.isEmpty()) {
			return;
		}
		log.debug("Сохранение условий");
		conditions.forEach((sensorId, condition) ->
				conditionRepository.save(condition)
		);
	}

	private void saveActions(@NonNull Map<String, Action> actions) {
		if (actions.isEmpty()) {
			return;
		}
		log.debug("Сохранение действий");
		actions.forEach((sensorId, action) ->
				actionRepository.save(action)
		);
	}
	
	private void updateScenario(@NonNull Scenario oldScenario, @NonNull Scenario newScenario) {
		log.info("Обновление сценария");

		if (newScenario.getConditions() != null) {
			saveConditions(newScenario.getConditions());
		}
		if (newScenario.getActions() != null) {
			saveActions(newScenario.getActions());
		}

		scenarioRepository.save(
				oldScenario.toBuilder()
						.name(newScenario.getName() == null ?
								oldScenario.getName() : newScenario.getName())
						.actions(newScenario.getActions() == null ?
								oldScenario.getActions() : newScenario.getActions())
						.conditions(newScenario.getConditions() == null ?
								oldScenario.getConditions() : newScenario.getConditions())
						.build()
		);
	}

}
