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
 * Действие, которое должно быть выполнено устройством
 * если было выполнено условие.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "actions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Action extends BaseEntity {

	/**
	 * Тип действия.
	 * <p>
	 * enum: ACTIVATE, DEACTIVATE, INVERSE, SET_VALUE
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	ActionType type;

	/// Необязательное значение, связанное с действием.
	Integer value;
}
