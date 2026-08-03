package ru.yandex.practicum.order.service;

@SuppressWarnings("unused")
public sealed interface RemoteCallResult<T>
		permits RemoteCallResult.Success,
		RemoteCallResult.Failure,
		RemoteCallResult.Degraded {

	record Success<T>(T value) implements RemoteCallResult<T> { }

	record Failure<T>(String message) implements RemoteCallResult<T> { }

	record Degraded<T>(Throwable cause) implements RemoteCallResult<T> { }
}