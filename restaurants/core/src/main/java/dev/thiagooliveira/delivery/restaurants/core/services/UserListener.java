package dev.thiagooliveira.delivery.restaurants.core.services;

import dev.thiagooliveira.delivery.restaurants.core.model.Address;

import java.util.UUID;

public interface UserListener {

    void updateDistance(UUID userId, Address userAddress);
}
