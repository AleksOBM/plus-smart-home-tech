package ru.yandex.practicum.telemetry.hub_router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HubRouterApp {
	public static void main(String[] args) {
		SpringApplication.run(HubRouterApp.class, args);
	}
}