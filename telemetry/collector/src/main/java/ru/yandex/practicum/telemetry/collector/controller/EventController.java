package ru.yandex.practicum.telemetry.collector.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.lang.NonNull;
import ru.yandex.practicum.grpc.telemetry.collector
		.CollectorControllerGrpc.CollectorControllerImplBase;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.telemetry.collector.handler.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.handler.SensorEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
public class EventController extends CollectorControllerImplBase {

	private final Map<SensorEventProto.PayloadCase, SensorEventHandler> sensorEventHandlers;
	private final Map<HubEventProto.PayloadCase, HubEventHandler> hubEventHandlers;

	public EventController(
			@NonNull Set<SensorEventHandler> sensorEventHandlers,
			@NonNull Set<HubEventHandler> hubEventHandlers
	) {
		// Преобразовываем набор хендлеров в map, где ключ — тип события от конкретного датчика или хаба.
		// Это нужно для упрощения поиска подходящего хендлера во время обработки событий
		this.sensorEventHandlers = sensorEventHandlers.stream()
				.collect(Collectors.toMap(
						SensorEventHandler::getMessageType,
						Function.identity()
				));

		this.hubEventHandlers = hubEventHandlers.stream()
				.collect(Collectors.toMap(
						HubEventHandler::getMessageType,
						Function.identity()
				));
	}

	/**
	 * Метод для обработки событий от датчиков.
	 * Вызывается при получении нового события от gRPC-клиента.
	 *
	 * @param request          Событие от датчика
	 * @param responseObserver Ответ для клиента
	 */
	@Override
	public void collectSensorEvent(@NonNull SensorEventProto request, StreamObserver<Empty> responseObserver) {

		log.info("Получен gRPC запрос:\n collectSensorEvent({})", request.getPayloadCase().name());

		try {
			// проверяем, есть ли обработчик для полученного события
			if (sensorEventHandlers.containsKey(request.getPayloadCase())) {
				// если обработчик найден, передаём событие ему на обработку
				sensorEventHandlers.get(request.getPayloadCase()).handle(request);
			} else {
				throw new IllegalArgumentException(
						"Не могу найти обработчик для события сенсора " + request.getPayloadCase());
			}

			// после обработки события возвращаем ответ клиенту
			responseObserver.onNext(Empty.getDefaultInstance());
			// и завершаем обработку запроса
			responseObserver.onCompleted();
		} catch (Exception e) {
			// в случае исключения отправляем ошибку клиенту
			responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
		}
	}

	@Override
	public void collectHubEvent(@NonNull HubEventProto request, StreamObserver<Empty> responseObserver) {

		log.info("\nПолучен gRPC запрос:\n collectHubEvent({})", request.getPayloadCase().name());

		try {
			if (hubEventHandlers.containsKey(request.getPayloadCase())) {
				hubEventHandlers.get(request.getPayloadCase()).handle(request);
			} else {
				throw new IllegalArgumentException(
						"Не могу найти обработчик для события хаба " + request.getPayloadCase());
			}

			responseObserver.onNext(Empty.getDefaultInstance());
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
		}
	}

}
