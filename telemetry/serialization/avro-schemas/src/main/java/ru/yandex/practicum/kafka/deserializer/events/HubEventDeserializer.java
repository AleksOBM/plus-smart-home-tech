package ru.yandex.practicum.kafka.deserializer.events;

import ru.yandex.practicum.kafka.deserializer.BaseAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

public class HubEventDeserializer extends BaseAvroDeserializer<SensorEventAvro> {

	public HubEventDeserializer() {
		super(HubEventAvro.getClassSchema());
	}

}
