package dev.thiagooliveira.delivery.restaurants.core.repositories;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantUserDistanceRepository {

    Optional<Double> getDistance(UUID restaurantId, UUID userId);
}
