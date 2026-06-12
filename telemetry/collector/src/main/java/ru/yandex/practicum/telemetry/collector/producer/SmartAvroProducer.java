package ru.yandex.practicum.telemetry.collector.producer;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.serializer.GeneralAvroSerializer;
import ru.yandex.practicum.telemetry.collector.model.Event;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class SmartAvroProducer {

	private final Producer<String, SpecificRecordBase> producer;

	public SmartAvroProducer(@Value("${sht.servers.bootstrap}") String bootstrapServer) {

		Properties config = new Properties();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class);

		this.producer = new KafkaProducer<>(config);
	}

	public <T extends Event> CompletableFuture<RecordMetadata> sendMessage(
			String topic,
			@Nonnull T event,
			SpecificRecordBase recordBase) {

		String key = event.getHubId();

		ProducerRecord<String, SpecificRecordBase> record =
				new ProducerRecord<>(
						topic,
						null,
						event.getTimestamp().toEpochMilli(),
						key,
						recordBase
				);

		CompletableFuture<RecordMetadata> future = new CompletableFuture<>();

		producer.send(record, (metadata, exception) -> {
			if (exception != null) {
				future.completeExceptionally(exception);
			} else {
				future.complete(metadata);
			}
		});

		return future;
	}
}
