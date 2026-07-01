package ru.yandex.practicum.telemetry.analyzer.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
public class AnalyzerConfiguration {

	@Bean
	@ConfigurationProperties("kafka.hub-event-consumer-config")
	public Properties hubEventConsumerConfig() {
		return new Properties();
	}

	@Bean
	@ConfigurationProperties("kafka.snapshot-consumer-config")
	public Properties snapshotConsumerConfig() {
		return new Properties();
	}

	@Bean(destroyMethod = "")
	public KafkaConsumer<String, HubEventAvro> hubEventConsumer(
			@Qualifier("hubEventConsumerConfig") Properties config
	) {
		return new KafkaConsumer<>(config);
	}

	@Bean(destroyMethod = "")
	public KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer(
			@Qualifier("snapshotConsumerConfig") Properties config
	) {
		return new KafkaConsumer<>(config);
	}
}
