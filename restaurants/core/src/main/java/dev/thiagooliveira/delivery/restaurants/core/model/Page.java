package dev.thiagooliveira.delivery.restaurants.core.model;

import java.util.List;

public class Page<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
