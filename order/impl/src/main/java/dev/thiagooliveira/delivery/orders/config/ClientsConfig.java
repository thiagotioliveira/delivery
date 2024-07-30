package dev.thiagooliveira.delivery.orders.config;

import dev.thiagooliveira.delivery.menus.spec.client.MenuApi;
import dev.thiagooliveira.delivery.restaurants.spec.client.RestaurantApi;
import dev.thiagooliveira.users.spec.client.UsersApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientsConfig {

    @Bean
    public UsersApi userApi(@Value("${app.client.users-service.baseUrl}") String baseUrl) {
        return new dev.thiagooliveira.users.spec.ApiClient()
                .setBasePath(baseUrl)
                .buildClient(UsersApi.class);
    }

    @Bean
    public RestaurantApi restaurantApi(@Value("${app.client.restaurants-service.baseUrl}") String baseUrl) {
        return new dev.thiagooliveira.delivery.restaurants.spec.ApiClient()
                .setBasePath(baseUrl)
                .buildClient(RestaurantApi.class);
    }

    @Bean
    public MenuApi menuApi(@Value("${app.client.menus-service.baseUrl}") String baseUrl) {
        return new dev.thiagooliveira.delivery.menus.spec.ApiClient()
                .setBasePath(baseUrl)
                .buildClient(MenuApi.class);
    }
}
