package ru.yandex.practicum.telemetry.analyzer.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.telemetry.analyzer.util.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Сценарий срабатывания указанных устройств
 * в определенных ситуациях,
 * для конкретного хаба
 */
@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "scenarios", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"hubId", "name"})
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Scenario extends BaseEntity {

	/// Идентификатор хаба
	@Column(nullable = false)
	String hubId;

	/// Название сценария
	@Column(nullable = false)
	String name;

	/**
	 * Описание проверяемых состояний датчиков
	 * <p>
	 * Map&lt;sensorId, Condition&gt;
	 */
	@Builder.Default
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "scenario_conditions",
			joinColumns = @JoinColumn(name = "scenario_id"),
			inverseJoinColumns = @JoinColumn(name = "condition_id"))
	@MapKeyColumn(table = "scenario_conditions", name = "sensor_id")
	Map<String, Condition> conditions = new HashMap<>();

	/**
	 * Описание действий устройств
	 * <p>
	 * Map&lt;sensorId, Action&gt;
	 */
	@Builder.Default
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "scenario_actions",
			joinColumns = @JoinColumn(name = "scenario_id"),
			inverseJoinColumns = @JoinColumn(name = "action_id"))
	@MapKeyColumn(table = "scenario_actions", name = "sensor_id")
	Map<String, Action> actions = new HashMap<>();
}
