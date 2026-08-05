package ru.yandex.practicum.infra.gateway.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Builder
@Configuration
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

	private List<UserConfig> users;
}