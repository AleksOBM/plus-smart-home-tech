package ru.yandex.practicum.commerce.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.commerce.interaction.client.warehouse.WarehouseClient;

@EnableFeignClients(basePackageClasses = WarehouseClient.class)
@ComponentScan(basePackages = {
		"ru.yandex.practicum.commerce.cart",
		"ru.yandex.practicum.commerce.interaction"})
@EnableDiscoveryClient
@SpringBootApplication
public class ShoppingCartApp {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApp.class, args);
	}
}
