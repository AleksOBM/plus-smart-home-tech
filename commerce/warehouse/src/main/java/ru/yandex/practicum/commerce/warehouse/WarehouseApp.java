package ru.yandex.practicum.commerce.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {
		"ru.yandex.practicum.commerce.warehouse",
		"ru.yandex.practicum.commerce.interaction"})
public class WarehouseApp {
	public static void main(String[] args) {
        SpringApplication.run(WarehouseApp.class, args);
	}
}
