package dev.thiagooliveira.delivery.restaurants.service;

import dev.thiagooliveira.delivery.restaurants.dto.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantService {

    RestaurantUserDetailsPage getAll(UUID userId, PageRequest pageRequest);

    RestaurantPage getAll(PageRequest pageRequest);

    Optional<RestaurantUserDetails> getById(UUID userId, UUID restaurantId);

    Optional<Restaurant> getById(UUID restaurantId);

    void deleteRestaurantsByUserId(UUID userId);

    Restaurant save(Restaurant restaurant);

    RestaurantUser save(RestaurantUser restaurantUser);

    List<RestaurantIdWithAddress> getByAddressStateAndAddressCountry(String state, String country);
}
