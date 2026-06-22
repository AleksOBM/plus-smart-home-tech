package ru.yandex.practicum.telemetry.aggregator.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.deserializer.events.SensorEventDeserializer;
import ru.yandex.practicum.kafka.serializer.GeneralAvroSerializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
public class AggregatorConfiguration {

	@Bean
	public Properties consumerConfig(@Value("${sht.servers.bootstrap}") String bootstrapServer,
	                                 @Value("${spring.application.name}") String groupId
	) {
		var properties = new Properties();
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SensorEventDeserializer.class);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		properties.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 512);
		properties.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1000);

		return properties;
	}

	@Bean
	public Properties producerConfig(@Value("${sht.servers.bootstrap}") String bootstrapServer) {
		var properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class);

		return properties;
	}

	@Bean(destroyMethod = "")
	public KafkaConsumer<String, SensorEventAvro> consumer(
			@Qualifier("consumerConfig") Properties config
	) {
		return new KafkaConsumer<>(config);
	}

	@Bean(destroyMethod = "")
	public KafkaProducer<String, SensorsSnapshotAvro> producer(
			@Qualifier("producerConfig") Properties config
	) {
		return new KafkaProducer<>(config);
	}
}
