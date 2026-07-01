package ru.yandex.practicum.telemetry.collector.model.hub.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Представляет действие, которое
 * должно быть выполнено устройством.
 */
@Getter
@Setter
@Builder
public class DeviceAction {

	/**
	 * Идентификатор датчика,
	 * связанного с действием.
	 */
	private String sensorId;

	/**
	 * Действие при срабатывании
	 * условия активации сценария.
	 */
	private ActionType type;

	/**
	 * Необязательное значение,
	 * связанное с действием.
	 */
	private Integer value;

}