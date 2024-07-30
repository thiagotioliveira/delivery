package dev.thiagooliveira.delivery.orders.validators.handlers;

import dev.thiagooliveira.delivery.orders.mappers.RestaurantMapper;
import dev.thiagooliveira.delivery.orders.spec.dto.CreateOrder;
import dev.thiagooliveira.delivery.restaurants.spec.client.RestaurantApi;
import dev.thiagooliveira.delivery.restaurants.spec.dto.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantOrderValidatorHandler implements OrderValidatorHandler {

    private final RestaurantApi restaurantApi;
    private final RestaurantMapper restaurantMapper;

    @Override
    public OrderValidatedMap validate(CreateOrder createOrder) {
        Restaurant restaurant =
                restaurantApi.getRestaurantById(createOrder.getRestaurantId().toString());
        var output = new OrderValidatedMap();
        output.putRestaurant(restaurantMapper.toRestaurant(restaurant));
        return output;
    }
}