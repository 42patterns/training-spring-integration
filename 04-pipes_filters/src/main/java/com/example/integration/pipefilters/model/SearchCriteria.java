package com.example.integration.pipefilters.model;

public class SearchCriteria {

	private String query;

	private Integer minPrice = 0;

	private Integer maxPrice = Integer.MAX_VALUE;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	@Override
	public String toString() {
		return "SearchCriteria{" +
				"query='" + query + '\'' +
				", minPrice=" + minPrice +
				", maxPrice=" + maxPrice +
				'}';
	}
}
