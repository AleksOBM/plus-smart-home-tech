package ru.yandex.practicum.telemetry.collector.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
public abstract class Event {

	/**
	 * Идентификатор хаба, связанного с событием.
	 */
	@NotBlank
	private String hubId;

	/**
	 * Временная метка события.
	 */
	@NotNull
	@Builder.Default
	private Instant timestamp = Instant.now();
}
