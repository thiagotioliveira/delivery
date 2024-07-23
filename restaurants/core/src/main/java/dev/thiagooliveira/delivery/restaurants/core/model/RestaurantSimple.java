package dev.thiagooliveira.delivery.restaurants.core.model;

import java.util.UUID;

public class RestaurantSimple {
    private UUID id;
    private String name;
    private String description;
    private String phoneNumber;
    private Address address;
    private Double distanceInMeters;
}
