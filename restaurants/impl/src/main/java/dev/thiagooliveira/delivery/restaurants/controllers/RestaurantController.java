package dev.thiagooliveira.delivery.restaurants.controllers;

import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetails;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetailsPage;
import dev.thiagooliveira.delivery.restaurants.exceptions.RestaurantNotFoundException;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantController implements RestaurantApi {

    private final RequestContextManager requestContextManager;
    private final RestaurantService restaurantService;

    @Override
    public ResponseEntity<RestaurantUserDetails> getRestaurantById(UUID restaurantId) {
        return ResponseEntity.ok(restaurantService
                .getById(requestContextManager.getUserAuthenticatedId(), restaurantId)
                .orElseThrow(RestaurantNotFoundException::new));
    }

    @Override
    public ResponseEntity<RestaurantUserDetailsPage> getRestaurants(Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(restaurantService.getAll(
                requestContextManager.getUserAuthenticatedId(),
                new PageRequest().pageNumber(pageNumber).pageSize(pageSize)));
    }
}
