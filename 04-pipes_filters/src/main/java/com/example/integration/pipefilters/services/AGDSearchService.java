package com.example.integration.pipefilters.services;

import com.example.integration.pipefilters.model.Product;
import com.example.integration.pipefilters.model.SearchCriteria;
import com.example.integration.pipefilters.model.SearchQueryRequest;
import com.example.integration.pipefilters.model.SearchResults;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class AGDSearchService {

    private final List<Product> warehouse = new ArrayList<>();

    public AGDSearchService() {
        warehouse.addAll(asList(
                new Product("telewizor Samsung", "TV", 1000, 1),
                new Product("radio Philips", 100, 2),
                new Product("laptop Samsung", 2000, 3),
                new Product("mikser Mulinex", 200, 4),
                new Product("lodówka Beko", 1200, 5),
                new Product("pralka Beko", 8000, 6),
                new Product("telewizor LG", "TV", 3000, 7),
                new Product("telewizor Sony", "TV", 4000, 8),
                new Product("telewizor Philips", "TV", 1900, 0),
                new Product("mikrofala Beko", 500, 9))
        );
    }

    public SearchResults queryForAGD(final SearchQueryRequest request) {
        final SearchCriteria criteria = request.getCriteria();

        return new SearchResults(criteria, warehouse.stream()
                .filter(Query.predicates(criteria))
                .collect(Collectors.toList()));
    }

    public SearchResults queryWithinCategory(final SearchQueryRequest request) {
        final SearchCriteria criteria = request.getCriteria();

        return new SearchResults(criteria, warehouse.stream()
                .filter(p -> p.getCategory() != request.getSubcategory())
                .filter(Query.predicates(criteria))
                .collect(Collectors.toList()));
    }

}
