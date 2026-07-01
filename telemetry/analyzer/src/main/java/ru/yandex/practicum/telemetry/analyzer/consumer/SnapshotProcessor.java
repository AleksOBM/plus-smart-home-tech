package ru.yandex.practicum.telemetry.analyzer.consumer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.analyzer.service.SnapshotService;
import ru.yandex.practicum.telemetry.utils.TimestampUtils;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotProcessor implements Runnable {

	@Value("${kafka.snapshot-consumer-config.topics}")
	private String topic;

	private final KafkaConsumer<String, SensorsSnapshotAvro> consumer;

	private final SnapshotService service;

	private final Map<TopicPartition, OffsetAndMetadata> lastOffsets = new HashMap<>();

	private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);

	@Getter
	private volatile boolean running = false;

	@PreDestroy
	public void shutdown() {
		log.info("Получил сигнал остановки");
		running = false;
		consumer.wakeup();
	}

	@Override
	public void run() {
		running = true;

		log.info("Подписка на топик: {}", topic);
		consumer.subscribe(Collections.singleton(topic));

		try {
			pollLoop();

		} catch (WakeupException ignored) {
			log.info("Получил интсрукцию wakeup");
		} catch (Exception e) {
			log.error("Ошибка во время обработки снапшотов", e);
		} finally {

			log.info("Остановка poll loop");
			running = false;

			try {
				log.info("Регистрация последнего смещения");
				if (!lastOffsets.isEmpty()) {
					consumer.commitSync(lastOffsets);
				}

			} finally {
				log.info("Остановка консьюмера");
				consumer.close();
			}
		}

	}

	private void pollLoop() {
		int iteration = 1;
		while (running) {
			log.debug("Проверка источника снапшотов");
			ConsumerRecords<String, SensorsSnapshotAvro> records = consumer
					.poll(CONSUME_ATTEMPT_TIMEOUT);
			if (records.isEmpty()) {
				log.debug("Источник снапшотов пуст");
				continue;
			}
			log.info("Итерация {}. Батч из {} записей получен. Начинаем обработку.",
					iteration++, records.count()
			);
			int recordNumber = 1;
			for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
				try {
					log.info("""
									Обработка снапшота №{}:
									{
									  "topic": "{}"
									  "partition": "{}"
									  "offset": "{}",
									  "timestamp": "{}"
									}
									""",
							recordNumber++,
							record.topic(),
							record.partition(),
							record.offset(),
							TimestampUtils.toString(record.timestamp())
					);

					service.analyzeSnapshot(record.value());

					log.debug("Сохранение смещения локально");
					lastOffsets.put(
							new TopicPartition(record.topic(), record.partition()),
							new OffsetAndMetadata(record.offset() + 1)
					);

				} catch (Exception e) {
					log.error("Ошибка обновления состояния:\n{}", record.value(), e);
					log.info("Остановка цикла");
					running = false;
					throw e;
				}
			}
		}
	}

}
