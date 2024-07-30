package dev.thiagooliveira.delivery.restaurants.service;

import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantPage;
import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantIdWithAddressProjection;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantUser;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantService {

    RestaurantPage getAll(PageRequest pageRequest);

    Optional<Restaurant> getById(UUID restaurantId);

    void deleteRestaurantsByUserId(UUID userId);

    Restaurant save(Restaurant restaurant);

    RestaurantUser save(RestaurantUser restaurantUser);

    List<RestaurantIdWithAddressProjection> findByAddressStateAndAddressCountry(String state, String country);
}
