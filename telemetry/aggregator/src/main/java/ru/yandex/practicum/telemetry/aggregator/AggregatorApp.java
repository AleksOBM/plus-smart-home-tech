package ru.yandex.practicum.telemetry.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.telemetry.aggregator.controller.AggregatorController;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AggregatorApp {
	public static void main(String[] args) {
		// Запуск Spring Boot приложения при помощи вспомогательного класса SpringApplication
		// метод run возвращает назад настроенный контекст, который мы можем использовать для
		// получения настроенных бинов
		ConfigurableApplicationContext context = SpringApplication.run(AggregatorApp.class, args);

		// Получаем бин AggregationController из контекста и запускаем основную логику сервиса
		AggregatorController aggregator = context.getBean(AggregatorController.class);
		aggregator.start();
	}
}
