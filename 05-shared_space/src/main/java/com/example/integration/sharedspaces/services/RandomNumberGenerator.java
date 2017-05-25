package com.example.integration.sharedspaces.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RandomNumberGenerator {

	private final Logger logger = LoggerFactory.getLogger(RandomNumberGenerator.class);
	private final Random random = new Random();

	public RandomGeneratorResult generateNumber(String key) {
		logger.info("{}@{} generates number", getClass().getSimpleName(), hashCode());

		int nextInt = random.nextInt(100);
		return new RandomGeneratorResult(key, nextInt);
	}

}
