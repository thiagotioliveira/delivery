package dev.thiagooliveira.delivery.orders.config.factories;

import dev.thiagooliveira.delivery.restaurants.clients.RestaurantAdminApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class RestaurantsApiFactory {
    private final ApplicationContext applicationContext;

    public RestaurantAdminApi create() {
        return applicationContext.getBean(RestaurantAdminApi.class);
    }
}
