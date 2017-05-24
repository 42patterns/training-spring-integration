package com.example.integration.pipefilters.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

	private String name;
	private int price;
	private int inStock;

	@JsonCreator
	public Product(@JsonProperty("name") String name,
				   @JsonProperty("price") int price,
				   @JsonProperty("inStock") int inStock) {
		this.name = name;
		this.price = price;
		this.inStock = inStock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

}
