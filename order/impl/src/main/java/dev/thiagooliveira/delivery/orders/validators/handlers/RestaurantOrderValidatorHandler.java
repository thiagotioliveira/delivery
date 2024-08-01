package dev.thiagooliveira.delivery.orders.validators.handlers;

import dev.thiagooliveira.delivery.orders.dto.CreateOrder;
import dev.thiagooliveira.delivery.orders.mappers.RestaurantMapper;
import dev.thiagooliveira.delivery.restaurants.clients.RestaurantAdminApi;
import dev.thiagooliveira.delivery.restaurants.dto.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestaurantOrderValidatorHandler implements OrderValidatorHandler {

    private final RestaurantAdminApi restaurantAdminApi;
    private final RestaurantMapper restaurantMapper;

    @Override
    public OrderValidatedMap validate(CreateOrder createOrder) {
        Restaurant restaurant = restaurantAdminApi.getRestaurantByIdAsAdmin(createOrder.getRestaurantId());
        log.debug("restaurant {} validated.", restaurant.getId());
        var output = new OrderValidatedMap();
        output.putRestaurant(restaurantMapper.toRestaurant(restaurant));
        return output;
    }
}
