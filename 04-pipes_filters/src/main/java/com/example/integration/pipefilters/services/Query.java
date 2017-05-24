package com.example.integration.pipefilters.services;

import com.example.integration.pipefilters.model.Product;
import com.example.integration.pipefilters.model.SearchCriteria;

import java.util.function.Predicate;

import static java.util.Objects.nonNull;

public interface Query {

    static Predicate<Product> predicates(SearchCriteria criteria) {
        Predicate<Product> namePredicate = product -> nonNull(criteria.getQuery()) && product.getName().contains(criteria.getQuery());
        Predicate<Product> minPricePredicate = product -> nonNull(criteria.getMinPrice()) && product.getPrice() > criteria.getMinPrice();
        Predicate<Product> maxPricePredicate = product -> nonNull(criteria.getMaxPrice()) && product.getPrice() <= criteria.getMaxPrice();

        return namePredicate.and(minPricePredicate).and(maxPricePredicate);
    }
}

