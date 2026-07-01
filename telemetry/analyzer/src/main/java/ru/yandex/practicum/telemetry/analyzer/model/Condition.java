package ru.yandex.practicum.telemetry.analyzer.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.telemetry.analyzer.util.BaseEntity;

/**
 * Условие сценария, проверяемое
 * на конкретном датчике.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "conditions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Condition extends BaseEntity {

	/**
	 * Тип проверяемого значения датчика.
	 * <p>
	 * enum: MOTION, LUMINOSITY, SWITCH, TEMPERATURE, CO2LEVEL, HUMIDITY
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	ConditionType type;

	/**
	 * Операция, применяемая к значеню.
	 * <p>
	 * enum: EQUALS, GREATER_THAN, LOWER_THAN
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	ConditionOperation operation;

	/// Значение, используемое в условии (может быть null).
	Integer value;
}
