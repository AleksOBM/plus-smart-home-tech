package ru.yandex.practicum.commerce.warehouse.repository;

import java.security.SecureRandom;
import java.util.Random;

public interface AddressRepository {

	String[] ADDRESSES = new String[] {"ADDRESS_1", "ADDRESS_2"};

	String CURRENT_ADDRESS = ADDRESSES[
			Random.from(new SecureRandom()).nextInt(0, ADDRESSES.length)
			];
}
