package ru.yandex.practicum.telemetry.collector.model.hub.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Условие сценария, которое содержит
 * информацию о датчике, типе условия,
 * операции и значении.
 */
@Getter
@Setter
@Builder
public class ScenarioCondition {

	/**
	 * Идентификатор датчика,
	 * связанного с условием.
	 */
	private String sensorId;

	/**
	 * Тип условия сценария.
	 */
	private ConditionType type;

	/**
	 * Используемая в условии операция.
	 */
	private ConditionOperation operation;

	/**
	 * Значение, используемое в условии.
	 */
	private Integer value;

}










