package ru.yandex.practicum.commerce.interaction.dto;

import lombok.Builder;

/// Представление адреса в системе
///
/// @param country Страна
/// @param city    Город
/// @param street  Улица
/// @param house   Дом
/// @param flat    Квартира
@Builder
public record AddressDto(
		String country,
		String city,
		String street,
		String house,
		String flat
) {
}
