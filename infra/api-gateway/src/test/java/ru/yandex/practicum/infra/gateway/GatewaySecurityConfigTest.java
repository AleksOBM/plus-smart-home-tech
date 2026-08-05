package ru.yandex.practicum.infra.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.yandex.practicum.infra.gateway.config.TestConfig;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Import(TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
class GatewaySecurityConfigTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void catalogGet_isPublic() {
		webTestClient.get()
				.uri("/api/products")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void orderCreate_withoutCredentials_isUnauthorized() {
		webTestClient.post()
				.uri("/api/orders")
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	void productWrite_withUserCredentials_isForbidden() {
		webTestClient.post()
				.uri("/api/products/1")
				.header(HttpHeaders.AUTHORIZATION,
						basic("ivan", "ivan"))
				.exchange()
				.expectStatus().isForbidden();
	}

	@Test
	void productWrite_withAdminCredentials_passesSecurity() {
		webTestClient.post()
				.uri("/api/products")
				.header(HttpHeaders.AUTHORIZATION,
						basic("anna", "anna"))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void unknownRoute_withAdminCredentials_isForbidden() {
		webTestClient.get()
				.uri("/unknown")
				.header(HttpHeaders.AUTHORIZATION,
						basic("anna", "anna"))
				.exchange()
				.expectStatus().isForbidden();
	}

	@Test
	void corsPreflight_isPublic() {
		webTestClient.options()
				.uri("/api/orders")
				.exchange()
				.expectStatus().isOk();
	}

	@NonNull
	private String basic(String username, String password) {
		String value = username + ":" + password;
		return "Basic " + Base64.getEncoder()
				.encodeToString(value.getBytes(StandardCharsets.UTF_8));
	}

}