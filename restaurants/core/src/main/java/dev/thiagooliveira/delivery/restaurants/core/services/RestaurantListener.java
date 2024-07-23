package dev.thiagooliveira.delivery.restaurants.core.services;

import dev.thiagooliveira.delivery.restaurants.core.model.Address;

import java.util.UUID;

public interface RestaurantListener {

    void updateDistance(UUID restaurantId, Address restaurantAddress);
}
