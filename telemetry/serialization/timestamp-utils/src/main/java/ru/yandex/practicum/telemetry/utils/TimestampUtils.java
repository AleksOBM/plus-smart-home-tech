package ru.yandex.practicum.telemetry.utils;

import com.google.protobuf.Timestamp;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class TimestampUtils {

	private final ZoneId zoneId = ZoneId.systemDefault();

	public final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public Timestamp toTimestamp(@NonNull Instant instant) {
		return Timestamp.newBuilder()
				.setSeconds(instant.getEpochSecond())
				.setNanos(instant.getNano())
				.build();
	}

	public Instant toInstant(@NonNull Timestamp timestamp) {
		return Instant.ofEpochSecond(
				timestamp.getSeconds(),
				timestamp.getNanos()
		);
	}

	public LocalDateTime toLocalDateTime(@NonNull Timestamp timestamp) {
		return LocalDateTime.ofInstant(TimestampUtils.toInstant(timestamp), zoneId);
	}

	public String toString(@NonNull Timestamp timestamp) {
		return FORMATTER.format(toLocalDateTime(timestamp));
	}

	public String toString(long longTimestamp) {
		return toString(toTimestamp(Instant.ofEpochSecond(longTimestamp).atZone(zoneId).toInstant()));
	}
}
