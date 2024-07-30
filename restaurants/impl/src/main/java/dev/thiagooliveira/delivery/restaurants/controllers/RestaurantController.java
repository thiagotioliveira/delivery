package dev.thiagooliveira.delivery.restaurants.controllers;

import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.dto.Restaurant;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantPage;
import dev.thiagooliveira.delivery.restaurants.exceptions.RestaurantNotFoundException;
import dev.thiagooliveira.delivery.restaurants.mappers.RestaurantMapper;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantController implements RestaurantApi {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @Override
    public ResponseEntity<Restaurant> getRestaurantById(String id) {
        Restaurant restaurant = restaurantMapper.toRestaurant(
                restaurantService.getById(UUID.fromString(id)).orElseThrow(RestaurantNotFoundException::new));
        return ResponseEntity.ok(restaurant);
    }

    @Override
    public ResponseEntity<RestaurantPage> getRestaurants(Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(restaurantService.getAll(
                new PageRequest().pageNumber(pageNumber).pageSize(pageSize)));
    }
}
