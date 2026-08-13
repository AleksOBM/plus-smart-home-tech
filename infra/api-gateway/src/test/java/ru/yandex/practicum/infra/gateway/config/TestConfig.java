package ru.yandex.practicum.infra.gateway.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@TestConfiguration
public class TestConfig {
	@Bean
	RouterFunction<ServerResponse> testBackendRoutes() {
		return RouterFunctions.route()
				.GET("/test/products",
						req -> ServerResponse.ok().build())
				.POST("/test/products",
						req -> ServerResponse.ok().build())
				.PATCH("/test/products/{id}",
						req -> ServerResponse.ok().build())

				.POST("/test/orders",
						req -> ServerResponse.ok().build())
				.GET("/test/orders",
						req -> ServerResponse.ok().build())
				.OPTIONS("/test/orders",
						req -> ServerResponse.ok().build())
				.build();
	}

	@Bean
	RouteLocator testRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("products", r -> r
						.path("/api/products")
						.uri("forward:/test/products"))
				.route("orders", r -> r
						.path("/api/orders")
						.uri("forward:/test/orders"))
				.build();
	}

}
