package ru.yandex.practicum.telemetry.aggregator.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SensorAggregatorService implements AggregatorService {

	/// Map <hubId, snapshot>
	private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

	@Override
	public Optional<SensorsSnapshotAvro> updateState(@NonNull SensorEventAvro event) {

		// Если это новый хаб - добавляем его id и пустой снапшот,
		// иначе - находим имеющийся снапшот с таким id.
		// Достаем снапшот по id хаба.
		var shot = snapshots.computeIfAbsent(
				event.getHubId(),
				hubId -> SensorsSnapshotAvro.newBuilder()
						.setHubId(hubId)
						.setTimestamp(event.getTimestamp())
						.setSensorsState(new HashMap<>())
						.build()
		);

		// Получаем состояния всех датчиков из снапшота
		Map<String, SensorStateAvro> states = shot.getSensorsState();

		// Ищем состояние события по id события
		var currentState = states.get(event.getId());

		// Если состояние не менялось - возвращаем пустой ответ
		if (currentState != null && checkState(currentState, event)) {
			return Optional.empty();
		}

		// Если состояние изменилось - заменяем на новое
		states.put(event.getId(), buildState(event));

		// Обновляем время последнего изменения снапшота.
		shot.setTimestamp(event.getTimestamp());

		return Optional.of(shot);
	}

	private SensorStateAvro buildState(@NonNull SensorEventAvro event) {
		return SensorStateAvro.newBuilder()
				.setTimestamp(event.getTimestamp())
				.setData(event.getPayload())
				.build();
	}

	private boolean checkState(
			@NonNull SensorStateAvro currentState,
			@NonNull SensorEventAvro event
	) {
		return currentState.getTimestamp().isAfter(event.getTimestamp())
				|| currentState.getData().equals(event.getPayload());
	}
}
