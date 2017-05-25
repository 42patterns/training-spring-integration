package com.example.integration.sharedspaces.services;

public class RandomGeneratorResult {

	private final String key;
	private final int value;

	public RandomGeneratorResult(String key, int value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public int getValue() {
		return value;
	}

}
