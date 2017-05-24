package com.example.integration.pipefilters.model;

import java.util.List;

public class SearchResults {

	private SearchCriteria criteria;
	private List<Product> found;

	public SearchResults() {
		super();
	}

	public SearchResults(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public SearchResults(SearchCriteria criteria, List<Product> found) {
		this.criteria = criteria;
		this.found = found;
	}

	public SearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	public List<Product> getFound() {
		return found;
	}

	public void setFound(List<Product> found) {
		this.found = found;
	}

}
