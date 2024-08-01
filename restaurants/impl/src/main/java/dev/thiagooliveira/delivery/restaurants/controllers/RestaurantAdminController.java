package dev.thiagooliveira.delivery.restaurants.controllers;

import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.dto.Restaurant;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantPage;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import java.lang.module.ResolutionException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantAdminController implements RestaurantAdminApi {

    private final RestaurantService restaurantService;

    @Override
    public ResponseEntity<Restaurant> getRestaurantByIdAsAdmin(UUID restaurantId) {
        return ResponseEntity.ok(restaurantService.getById(restaurantId).orElseThrow(ResolutionException::new));
    }

    @Override
    public ResponseEntity<RestaurantPage> getRestaurantsAsAdmin(Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(restaurantService.getAll(
                new PageRequest().pageNumber(pageNumber).pageSize(pageSize)));
    }
}
