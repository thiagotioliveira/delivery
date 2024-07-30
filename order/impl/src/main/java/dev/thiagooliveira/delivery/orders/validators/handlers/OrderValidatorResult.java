package dev.thiagooliveira.delivery.orders.validators.handlers;

import static dev.thiagooliveira.delivery.orders.validators.handlers.OrderValidatedMap.*;

import dev.thiagooliveira.delivery.orders.spec.dto.OrderItem;
import dev.thiagooliveira.delivery.orders.spec.dto.Restaurant;
import dev.thiagooliveira.delivery.orders.spec.dto.User;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class OrderValidatorResult {

    private final Restaurant restaurant;
    private final User user;
    private final List<OrderItem> items;

    public OrderValidatorResult(Map<String, Object> map) {
        this.restaurant = (Restaurant) map.get(KEY_RESTAURANT);
        this.user = (User) map.get(KEY_USER);
        this.items = (List<OrderItem>) map.get(KEY_ITEMS);
    }
}
