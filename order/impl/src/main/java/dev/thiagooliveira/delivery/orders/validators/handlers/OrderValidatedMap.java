package dev.thiagooliveira.delivery.orders.validators.handlers;

import dev.thiagooliveira.delivery.orders.dto.OrderItem;
import dev.thiagooliveira.delivery.orders.dto.Restaurant;
import dev.thiagooliveira.delivery.orders.dto.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OrderValidatedMap {

    public static final String KEY_RESTAURANT = "restaurant-key";
    public static final String KEY_USER = "user-key";
    public static final String KEY_ITEMS = "items-key";

    private final Map<String, Object> map;

    public OrderValidatedMap() {
        this.map = new HashMap<>();
    }

    public OrderValidatedMap(Map<String, Object> map) {
        this.map = new HashMap<>(map);
    }

    public Stream<Map.Entry<String, Object>> entrySetStream() {
        return this.map.entrySet().stream();
    }

    public Restaurant getRestaurant() {
        if (this.map.containsKey(KEY_RESTAURANT)) {
            return (Restaurant) this.map.get(KEY_RESTAURANT);
        }
        return null;
    }

    public void putRestaurant(Restaurant restaurant) {
        this.map.put(KEY_RESTAURANT, restaurant);
    }

    public User getUser() {
        if (this.map.containsKey(KEY_USER)) {
            return (User) this.map.get(KEY_USER);
        }
        return null;
    }

    public void putUser(User user) {
        this.map.put(KEY_USER, user);
    }

    public List<OrderItem> getItems() {
        if (this.map.containsKey(KEY_ITEMS)) {
            return (List<OrderItem>) this.map.get(KEY_ITEMS);
        }
        return null;
    }

    public void putItems(List<OrderItem> items) {
        this.map.put(KEY_ITEMS, items);
    }
}
