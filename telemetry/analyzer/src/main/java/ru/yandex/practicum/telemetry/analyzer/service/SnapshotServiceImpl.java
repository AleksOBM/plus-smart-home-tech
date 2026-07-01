package ru.yandex.practicum.telemetry.analyzer.service;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc
		.HubRouterControllerBlockingStub;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.analyzer.model.Condition;
import ru.yandex.practicum.telemetry.analyzer.model.ConditionOperation;
import ru.yandex.practicum.telemetry.analyzer.model.Scenario;
import ru.yandex.practicum.telemetry.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.telemetry.utils.TimestampUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnapshotServiceImpl implements SnapshotService {

	@GrpcClient("hub-router")
	private HubRouterControllerBlockingStub hubRouterClient;

	private final ScenarioRepository scenarioRepository;

	@Override
	public void analyzeSnapshot(@NonNull SensorsSnapshotAvro snapshot) throws RuntimeException {
		log.info("Начинаем анализ снапшота: {}", snapshot);
		var hubId = snapshot.getHubId();

		log.debug("Получаем сценарии для хаба {}", hubId);
		Set<Scenario> scenarios = scenarioRepository.findByHubId(hubId);
		if (scenarios.isEmpty()) {
			log.warn("Сценарии хаба не найдены");
			return;
		}

		log.debug("Получаем состояния устройств из снапшота");
		var states = snapshot.getSensorsState();
		if (states == null || states.isEmpty()) {
			throw new RuntimeException("Снапшот не содержит состояний: " + snapshot);
		}

		log.debug("Проходимся по всем сценариям этого хаба");
		for (Scenario scenario : scenarios) {

			log.debug("Получаем условия из сценария");
			var conditions = scenario.getConditions();
			if (conditions == null) {
				throw new RuntimeException("Сценарий не содержит условий: " + scenario);
			}

			log.debug("Проверяем условия сценария, если не выполняются - выходим");
			if (!analyzeConditions(conditions, states)) {
				log.info("Условия сценария не выполняются");
				continue;
			}

			log.debug("Отправляем запрос выполнения в hub-router");
			sendAction(scenario, snapshot);
		}

	}

	private boolean analyzeConditions(@NonNull Map<String, Condition> conditions,
	                                  Map<String, SensorStateAvro> states
	) throws RuntimeException {

		log.trace("Создаем хранилище для результатов проверок");
		var conditionsResults = new ArrayList<Boolean>();

		log.debug("Проходимся по всем условиям этого сценария");
		conditions.forEach((sensorId, condition) -> {

			log.debug("Получаем текущее состояние сенсора, указанного в условии");
			var currentState = states.get(sensorId);

			if (currentState == null) {
				log.debug("Состояние сенсора = null");
				conditionsResults.add(false);
				return;
			}

			log.debug("Получаем данные о событии состояния");
			var stateEvent = currentState.getData();

			log.debug("В зависимости от типа сенсора определяем выполняется ли условие");
			switch (stateEvent) {
				case ClimateSensorAvro event -> conditionsResults.add(checkCondition(event, condition));
				case LightSensorAvro event -> conditionsResults.add(checkCondition(event, condition));
				case MotionSensorAvro event -> conditionsResults.add(checkCondition(event, condition));
				case SwitchSensorAvro event -> conditionsResults.add(checkCondition(event, condition));
				case TemperatureSensorAvro event -> conditionsResults.add(checkCondition(event, condition));
				default -> throw new IllegalStateException("Неопознанный тип события: " + stateEvent);
			}
		});

		log.debug("Проверяем на ошибки");
		if (conditionsResults.contains(null)) {
			throw new RuntimeException("Ошибка при определении условия");
		}

		log.debug("Возвращаем true если список не пуст и все этементы в нем true");
		return !conditionsResults.isEmpty() && conditionsResults.stream().allMatch(Boolean.TRUE::equals);
	}

	private void sendAction(@NonNull Scenario scenario, @NonNull SensorsSnapshotAvro snapshot)
			throws RuntimeException {

		Instant instant = Instant.now();

		scenario.getActions().forEach((sensorId, action) -> {

			var request = DeviceActionRequest.newBuilder()
					.setHubId(snapshot.getHubId())
					.setScenarioName(scenario.getName())
					.setAction(DeviceActionProto.newBuilder()
							.setSensorId(sensorId)
							.setType(ActionTypeProto.valueOf(action.getType().name()))
							.setValue(action.getValue())
							.build()
					)
					.setTimestamp(TimestampUtils.toTimestamp(instant))
					.build();

			log.info("""
					Отправка gRPC сообщения:
					{
					  "hub_id": "{}",
					  "scenario_name": "{}",
					  "action": {
					    "sensor_id": "{}",
					    "type": "{}",
					    "value": "{}"
					  },
					  "timestamp": "{}"
					}
					""",
					request.getHubId(),
					request.getScenarioName(),
					request.getAction().getSensorId(),
					request.getAction().getType(),
					request.getAction().getValue(),
					TimestampUtils.toString(request.getTimestamp())
					);

			try {
				var response = hubRouterClient.handleDeviceAction(request);
				if (response == null) {
					throw new RuntimeException("Не получен отчет о доставке сообщения от хаб-роутера");
				}
			} catch (StatusRuntimeException e) {
				log.error("Ошибка gRPC при отправке действия", e);
				throw new RuntimeException("Ошибка отправки действия через хаб-роутер", e);
			}
		});
	}

	private boolean checkOperation(@NonNull ConditionOperation operation, int v1, int v2) {
		return switch (operation) {
			case GREATER_THAN -> v1 > v2;
			case LOWER_THAN -> v1 < v2;
			case EQUALS -> v1 == v2;
		};
	}

	private @Nullable Boolean checkCondition(@NonNull ClimateSensorAvro event,
	                                         @NonNull Condition condition
	) {
		switch (condition.getType()) {

			case TEMPERATURE -> {
				return checkOperation(condition.getOperation(), event.getTemperatureC(), condition.getValue());
			}
			case HUMIDITY -> {
				return checkOperation(condition.getOperation(), event.getHumidity(), condition.getValue());
			}
			case CO2LEVEL -> {
				return checkOperation(condition.getOperation(), event.getCo2Level(), condition.getValue());
			}
			default -> {
				return null;
			}
		}
	}

	private @Nullable Boolean checkCondition(@NonNull LightSensorAvro event,
	                                         @NonNull Condition condition
	) {
		switch (condition.getType()) {
			case LUMINOSITY -> {
				return checkOperation(condition.getOperation(), event.getLuminosity(), condition.getValue());
			}
			default -> {
				return null;
			}
		}
	}

	private @Nullable Boolean checkCondition(@NonNull MotionSensorAvro event,
	                                         @NonNull Condition condition
	) {
		switch (condition.getType()) {
			case MOTION -> {
				switch (condition.getOperation()) {
					case EQUALS -> {
						return event.getMotion() == condition.getValue() > 0;
					}
					default -> {
						return null;
					}
				}
			}
			default -> {
				return null;
			}
		}
	}

	private @Nullable Boolean checkCondition(@NonNull SwitchSensorAvro event,
	                                         @NonNull Condition condition
	) {
		switch (condition.getType()) {
			case SWITCH -> {
				switch (condition.getOperation()) {
					case EQUALS -> {
						return event.getState() == condition.getValue() > 0;
					}
					default -> {
						return null;
					}
				}
			}
			default -> {
				return null;
			}
		}
	}

	private @Nullable Boolean checkCondition(@NonNull TemperatureSensorAvro event,
	                                         @NonNull Condition condition
	) {
		switch (condition.getType()) {
			case TEMPERATURE -> {
				return checkOperation(condition.getOperation(), event.getTemperatureC(), condition.getValue());
			}
			default -> {
				return null;
			}
		}
	}

}
