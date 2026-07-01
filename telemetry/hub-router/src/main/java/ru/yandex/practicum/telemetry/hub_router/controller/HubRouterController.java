package ru.yandex.practicum.telemetry.hub_router.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.lang.NonNull;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.telemetry.utils.TimestampUtils;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class HubRouterController extends HubRouterControllerGrpc.HubRouterControllerImplBase {

	@Override
	public void handleDeviceAction(@NonNull DeviceActionRequest request,
	                               StreamObserver<Empty> responseObserver
	) {
		log.info("""
						Обработка события:
						{
						  "hubId": "{}",
						  "scenarioName": "{}",
						  "action": {
						    "sensorId": "{}",
						    "type": "{}",
						    "value": {}
						    },
						  "timestamp": "{}"
						}
						""",
				request.getHubId(),
				request.getScenarioName(),
				request.getAction().getSensorId(),
				request.getAction().getType(),
				request.getAction().getValue(),
				TimestampUtils.toString(request.getTimestamp())
		);

		try {
			responseObserver.onNext(Empty.getDefaultInstance());
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
		}
	}

}
