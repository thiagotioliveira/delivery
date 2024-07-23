package dev.thiagooliveira.delivery.restaurants.core.services;

import dev.thiagooliveira.delivery.restaurants.core.model.*;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantService {

    Restaurant create(CreateRestaurant createRestaurant);

    Restaurant update(UUID id, UpdateRestaurant updateRestaurant);

    void delete(UUID id);

    Optional<RestaurantSimple> get(UUID restaurantId, UUID userId);

    Page<RestaurantSimple> list(UUID userId, double maxDistance, PageRequest pageRequest);
}
