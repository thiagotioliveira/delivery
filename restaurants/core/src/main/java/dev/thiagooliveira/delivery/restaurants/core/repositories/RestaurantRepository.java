package dev.thiagooliveira.delivery.restaurants.core.repositories;

import dev.thiagooliveira.delivery.restaurants.core.model.Page;
import dev.thiagooliveira.delivery.restaurants.core.model.PageRequest;
import dev.thiagooliveira.delivery.restaurants.core.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.core.model.RestaurantSimple;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);


    Page<RestaurantSimple> findByUserIdAndMaxDistance(UUID userId, double maxDistance, PageRequest pageRequest);
}
