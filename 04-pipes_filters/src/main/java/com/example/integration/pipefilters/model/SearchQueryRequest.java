package com.example.integration.pipefilters.model;

public class SearchQueryRequest {
	
	private String category;
	
	private SearchCriteria criteria;

	public SearchQueryRequest() {
		super();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public SearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public String toString() {
		return "SearchQueryRequest{" +
				"category='" + category + '\'' +
				", criteria=" + criteria +
				'}';
	}
}
