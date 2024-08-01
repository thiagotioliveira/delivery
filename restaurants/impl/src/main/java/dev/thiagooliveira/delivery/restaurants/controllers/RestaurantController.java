package dev.thiagooliveira.delivery.restaurants.controllers;

import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetails;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetailsPage;
import dev.thiagooliveira.delivery.restaurants.exceptions.RestaurantNotFoundException;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantController implements RestaurantApi {

    private final RestaurantService restaurantService;

    @Override
    public ResponseEntity<RestaurantUserDetails> getRestaurantById(UUID restaurantId) {
        return ResponseEntity.ok(restaurantService
                .getById(getUserRequest(), restaurantId)
                .orElseThrow(RestaurantNotFoundException::new));
    }

    @Override
    public ResponseEntity<RestaurantUserDetailsPage> getRestaurants(Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(restaurantService.getAll(
                getUserRequest(), new PageRequest().pageNumber(pageNumber).pageSize(pageSize)));
    }

    private static UUID getUserRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }
}
