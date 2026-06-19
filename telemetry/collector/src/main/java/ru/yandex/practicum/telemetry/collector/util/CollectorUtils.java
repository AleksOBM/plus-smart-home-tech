package ru.yandex.practicum.telemetry.collector.util;

import com.google.protobuf.Timestamp;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

@UtilityClass
public class CollectorUtils {

	public Instant toInstant(@NonNull Timestamp timestamp) {
		return Instant.ofEpochSecond(
				timestamp.getSeconds(),
				timestamp.getNanos()
		);
	}
}
