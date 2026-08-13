package ru.yandex.practicum.order.fallback;

import feign.FeignException;

import java.util.function.Predicate;

@SuppressWarnings("unused")
public class FailurePredicate implements Predicate<Throwable> {

	@Override
	public boolean test(Throwable throwable) {

		if (throwable instanceof FeignException ex) {
			return ex.status() != 404 && ex.status() != 409;
		}

		return true;
	}
}
