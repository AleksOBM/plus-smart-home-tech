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

	/**
	 * Map <hubId, snapshot>
	 */
	private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

	@Override
	public Optional<SensorsSnapshotAvro> updateState(@NonNull SensorEventAvro event) {

		var snapshot = snapshots.computeIfAbsent(
				event.getHubId(),
				hubId -> SensorsSnapshotAvro.newBuilder()
						.setHubId(hubId)
						.setTimestamp(event.getTimestamp())
						.setSensorsState(new HashMap<>())
						.build()
		);

		var currentState = snapshot.getSensorsState().get(event.getId());

		// Игнорируем устаревшие события и дубликаты.
		if (currentState != null && checkState(currentState, event)) {
			return Optional.empty();
		}

		snapshot.getSensorsState().put(
				event.getId(),
				buildState(event)
		);

		// Время последнего изменения snapshot.
		snapshot.setTimestamp(event.getTimestamp());

		return Optional.of(snapshot);
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
