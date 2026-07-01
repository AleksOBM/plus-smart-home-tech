package ru.yandex.practicum.kafka.deserializer.events;

import ru.yandex.practicum.kafka.deserializer.BaseAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public class HubEventDeserializer extends BaseAvroDeserializer<HubEventAvro> {

	public HubEventDeserializer() {
		super(HubEventAvro.getClassSchema());
	}

}
