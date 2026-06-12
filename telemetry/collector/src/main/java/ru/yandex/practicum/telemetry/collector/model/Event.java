package ru.yandex.practicum.telemetry.collector.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class Event {

	/** Идентификатор хаба, связанного с событием. */
	@NotBlank
	private String hubId;

	/** Временная метка события. */
	@NotNull
	private Instant timestamp = Instant.now();
}
