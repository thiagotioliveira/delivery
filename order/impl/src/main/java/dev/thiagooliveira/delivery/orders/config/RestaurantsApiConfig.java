package dev.thiagooliveira.delivery.orders.config;

import dev.thiagooliveira.delivery.orders.config.properties.AppProperties;
import dev.thiagooliveira.delivery.restaurants.clients.RestaurantAdminApi;
import dev.thiagooliveira.delivery.restaurants.clients.invokers.ApiClient;
import dev.thiagooliveira.delivery.restaurants.clients.invokers.auth.OauthClientCredentialsGrant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantsApiConfig {

    @Bean
    public RestaurantAdminApi restaurantsAdminApi(AppProperties appProperties) {
        ApiClient apiClient = new ApiClient()
                .setBasePath(appProperties.getClient().getRestaurantsService().getBaseUrl());
        apiClient.addAuthorization("ClientCredentials", buildOauthClientCredentialsGrant(appProperties.getKeycloak()));
        return apiClient.buildClient(RestaurantAdminApi.class);
    }

    private OauthClientCredentialsGrant buildOauthClientCredentialsGrant(AppProperties.Keycloak keycloak) {
        OauthClientCredentialsGrant grant = new OauthClientCredentialsGrant(
                "",
                String.format("%s/realms/%s/protocol/openid-connect/token", keycloak.getBaseUrl(), keycloak.getRealm()),
                "profile");
        grant.configure(keycloak.getClientId(), keycloak.getClientSecret());
        return grant;
    }
}
