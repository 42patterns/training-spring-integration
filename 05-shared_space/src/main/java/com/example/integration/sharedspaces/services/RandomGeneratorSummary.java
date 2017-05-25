package com.example.integration.sharedspaces.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RandomGeneratorSummary {

	private String key;
	private List<Integer> integers;
	private long average;

	@JsonCreator
	public RandomGeneratorSummary(@JsonProperty("key") String key,
								  @JsonProperty("integers") List<Integer> integers,
								  @JsonProperty("average") long average) {
		this.key = key;
		this.integers = integers;
		this.average = average;
	}

	public String getKey() {
		return key;
	}

	public List<Integer> getIntegers() {
		return integers;
	}

	public long getAverage() {
		return average;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setIntegers(List<Integer> integers) {
		this.integers = integers;
	}

	public void setAverage(long average) {
		this.average = average;
	}

}