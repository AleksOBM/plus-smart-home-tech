package ru.yandex.practicum.infra.gateway.security;

import lombok.Builder;

import java.util.List;

@Builder
public record UserConfig(
		String username,
		String password,
		List<String> roles
) {
}