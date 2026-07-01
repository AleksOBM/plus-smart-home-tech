package ru.yandex.practicum.telemetry.analyzer.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.telemetry.analyzer.consumer.HubEventProcessor;
import ru.yandex.practicum.telemetry.analyzer.consumer.SnapshotProcessor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyzerRunner implements CommandLineRunner {

	private final HubEventProcessor hubEventProcessor;
	private final SnapshotProcessor snapshotProcessor;

	private int cycleNumber = 0;

	@Override
	public void run(String... args) {

		log.info("Цикл запуска №{}", ++cycleNumber);

		if(!hubEventProcessor.isRunning()) {
			Thread hubEventsThread = new Thread(hubEventProcessor);
			hubEventsThread.setName("HubEventHandlerThread");

			log.info("Запускаем HubEventProcessor в отдельном потоке");
			hubEventsThread.start();
		} else {
			log.info("HubEventProcessor уже запущен");
		}

		if(!snapshotProcessor.isRunning()) {
			if (!hubEventProcessor.isRunning()) {
				log.info("HubEventProcessor не запущен");
				return;
			}
			log.info("Запускаем SnapshotProcessor в текущем потоке");
			snapshotProcessor.run();
		} else {
			log.info("SnapshotProcessor уже запущен");
		}

	}
}
