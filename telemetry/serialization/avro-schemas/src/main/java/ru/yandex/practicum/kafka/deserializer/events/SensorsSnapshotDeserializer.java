package ru.yandex.practicum.kafka.deserializer.events;

import ru.yandex.practicum.kafka.deserializer.BaseAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

@SuppressWarnings("unused")
public class SensorsSnapshotDeserializer extends BaseAvroDeserializer<SensorsSnapshotAvro> {

	public SensorsSnapshotDeserializer() {
		super(SensorsSnapshotAvro.getClassSchema());
	}
}
