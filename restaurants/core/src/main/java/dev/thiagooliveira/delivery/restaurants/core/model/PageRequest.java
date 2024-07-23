package dev.thiagooliveira.delivery.restaurants.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PageRequest {
    private int pageNumber;
    private int pageSize;
}
