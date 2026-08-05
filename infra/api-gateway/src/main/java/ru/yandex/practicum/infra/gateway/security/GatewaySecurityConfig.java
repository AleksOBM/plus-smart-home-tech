package ru.yandex.practicum.infra.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(@NonNull ServerHttpSecurity http) {
		return http
				// и правила доступа к маршрутам
				.authorizeExchange(exchanges -> exchanges

						// Публичные маршруты
						.pathMatchers(HttpMethod.OPTIONS, "/**"
						).permitAll()
						.pathMatchers(HttpMethod.GET, "/api/products/**"
						).permitAll()
						.pathMatchers(HttpMethod.GET, "/api/categories/**"
						).permitAll()
						.pathMatchers(HttpMethod.GET, "/api/inventory/**"
						).permitAll()
						.pathMatchers(
								"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
						).permitAll()

						// Пользовательские маршруты
						.pathMatchers(HttpMethod.POST, "/api/orders"
						).hasAnyRole("USER", "ADMIN")
						.pathMatchers(HttpMethod.GET, "/api/orders/by-email"
						).hasAnyRole("USER", "ADMIN")
						.pathMatchers(HttpMethod.GET, "/api/orders/{id}"
						).hasAnyRole("USER", "ADMIN")

						// Административные маршруты
						.pathMatchers(HttpMethod.GET, "/api/orders"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.POST, "/api/products/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.PUT, "/api/products/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.PATCH, "/api/products/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.DELETE, "/api/products/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.POST, "/api/categories/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.PUT, "/api/categories/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.DELETE, "/api/categories/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.POST, "/api/inventory/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.PUT, "/api/inventory/**"
						).hasRole("ADMIN")
						.pathMatchers(HttpMethod.DELETE, "/api/inventory/**"
						).hasRole("ADMIN")
						.pathMatchers("/api/inventory/reserve"
						).hasAnyRole("USER", "ADMIN")
						.pathMatchers("/api/inventory/release"
						).hasAnyRole("USER", "ADMIN")

						// всем остальным отказать
						.anyExchange().denyAll())

				.httpBasic(withDefaults())
				.cors(withDefaults())
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Источник пользователей для реактивной security-конфигурации
	@Bean
	public MapReactiveUserDetailsService userDetailsService(
			PasswordEncoder passwordEncoder,
			@NonNull SecurityProperties securityProperties
	) {
		List<UserDetails> users = securityProperties.getUsers().stream()
				.map(userConfig -> User.builder()
						.username(userConfig.username())
						.password(passwordEncoder.encode(userConfig.password()))
						.roles(userConfig.roles().toArray(new String[0]))
						.build()
				)
				.toList();
		return new MapReactiveUserDetailsService(users);
	}

}
