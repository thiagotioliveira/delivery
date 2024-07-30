package dev.thiagooliveira.delivery.orders.config;

import dev.thiagooliveira.delivery.menus.clients.MenuApi;
import dev.thiagooliveira.delivery.restaurants.clients.RestaurantApi;
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
        return new dev.thiagooliveira.delivery.restaurants.ApiClient()
                .setBasePath(baseUrl)
                .buildClient(RestaurantApi.class);
    }

    @Bean
    public MenuApi menuApi(@Value("${app.client.menus-service.baseUrl}") String baseUrl) {
        return new dev.thiagooliveira.delivery.menus.ApiClient()
                .setBasePath(baseUrl)
                .buildClient(MenuApi.class);
    }
}
