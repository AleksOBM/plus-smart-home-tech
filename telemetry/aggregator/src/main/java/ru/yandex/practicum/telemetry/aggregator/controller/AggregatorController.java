package ru.yandex.practicum.telemetry.aggregator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.aggregator.service.AggregatorService;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, ответственный за запуск агрегации данных.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AggregatorController {

	@Value("${sht.aggregator.topics.sensors}")
	private String sensorsTopic;

	@Value("${sht.aggregator.topics.snapshots}")
	private String snapshotsTopic;

	private final KafkaConsumer<String, SensorEventAvro> consumer;
	private final KafkaProducer<String, SensorsSnapshotAvro> producer;
	private final AggregatorService service;

	private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);

	/// Храним последний обработанный offset для каждой partition.
	private final Map<TopicPartition, OffsetAndMetadata> lastOffsets = new HashMap<>();

	private volatile boolean running = true;

	@PreDestroy
	public void shutdown() {
		log.info("Получен сигнал остановки");
		running = false;
		consumer.wakeup();
	}

	/**
	 * Метод для начала процесса агрегации данных.
	 * Подписывается на топики для получения событий от датчиков,
	 * формирует снимок их состояния и записывает в кафку.
	 */
	public void start() {
		try {

			consumer.subscribe(List.of(sensorsTopic));
			log.info("Оформлена подписка на топик: {}", sensorsTopic);

			log.info("Запуск цикла обработки событий");
			pollLoop();

		} catch (WakeupException ignored) {
			log.info("Потребитель получил интсрукцию wakeup");
		} catch (Exception e) {
			log.error("Ошибка во время обработки событий от датчиков", e);
		} finally {

			try {
				log.info("Отправка оставшихся снапшотов");
				producer.flush();
				log.info("Синхронная регистрация последнего смещения");
				if (!lastOffsets.isEmpty()) {
					consumer.commitSync(lastOffsets);
				}

			} finally {
				log.info("Закрываем консьюмер");
				consumer.close();
				log.info("Закрываем продюсер");
				producer.close();
			}
		}
	}

	private void pollLoop() {
		int iteration = 1;
		while (running) {

			log.debug("Проверка источника");
			ConsumerRecords<String, SensorEventAvro> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);

			if (records.isEmpty()) {
				log.debug("Источник пуст");
				continue;
			}
			log.info("Итерация {}. Батч из {} записей получен. Начинаем обработку.", iteration, records.count());
			int recordNumber = 1;
			for (ConsumerRecord<String, SensorEventAvro> record : records) {
				try {
					log.info("Обработка записи №{}:\nтопик = {}, партиция = {}, смещение = {}, значение: {}",
							recordNumber, record.topic(), record.partition(), record.offset(), record.value());

					service.updateState(record.value()).ifPresent(snapshot -> {
								log.debug("Состояние снапшота обновлено. Отправляем данные в кафку.");
								producer.send(
										new ProducerRecord<>(snapshotsTopic, snapshot),
										(metadata, exception) -> {
											if (exception != null) {
												log.error("Ошибка отправки снапшота", exception);
											}
										}
								);
							}
					);

					log.debug("Сохраняем смещения локально");
					lastOffsets.put(
							new TopicPartition(record.topic(), record.partition()),
							new OffsetAndMetadata(record.offset() + 1)
					);

				} catch (Exception e) {
					log.error("Ошибка обновления состояния:\nтопик = {}, партиция = {}, смещение = {}",
							record.topic(), record.partition(), record.offset(), e);
					throw e;
				}

				recordNumber++;
			}
			iteration++;

			if (!records.isEmpty()) {
				log.info("Регистрируем смещения в кафке\n");
				commitOffsets();
			}
		}
	}

	private void commitOffsets() {
		if (lastOffsets.isEmpty()) {
			log.info("Локальные смещения отсутствуют");
			return;
		}

		Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>(lastOffsets);
		consumer.commitAsync(offsetsToCommit, (offsets, exception) -> {
			if (exception != null) {
				log.error("Регистрация смещений в кафке не удалась:\n{}", offsets, exception);
			} else {
				log.debug("Смещения зарегистрированы в кафке:\n{}", offsets);
			}
		});
	}

}