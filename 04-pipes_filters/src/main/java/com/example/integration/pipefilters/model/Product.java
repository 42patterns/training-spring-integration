package com.example.integration.pipefilters.model;

public class Product {

	private String name;
	private String category;
	private int price;
	private int inStock;

	public Product(String name, int price, int inStock) {
		this.name = name;
		this.price = price;
		this.inStock = inStock;
	}

	public Product(String name, String category, int price, int inStock) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.inStock = inStock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
