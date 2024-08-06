package dev.thiagooliveira.delivery.menus.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import dev.thiagooliveira.delivery.menus.config.factories.RestaurantAdminApiFactory;
import dev.thiagooliveira.delivery.menus.config.properties.AppProperties;
import dev.thiagooliveira.delivery.menus.exceptions.ServiceInstanceNotFoundException;
import dev.thiagooliveira.delivery.restaurants.clients.RestaurantAdminApi;
import dev.thiagooliveira.delivery.restaurants.clients.invokers.ApiClient;
import dev.thiagooliveira.delivery.restaurants.clients.invokers.auth.OauthClientCredentialsGrant;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RestaurantsApiConfig {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public RestaurantAdminApi restaurantsAdminApi(AppProperties appProperties, LoadBalancerClient loadBalancerClient) {
        ServiceInstance instance = loadBalancerClient.choose(
                appProperties.getClient().getRestaurantsService().getServiceId());
        if (instance == null) {
            throw new ServiceInstanceNotFoundException(
                    appProperties.getClient().getRestaurantsService().getServiceId());
        }
        ApiClient apiClient = new ApiClient().setBasePath(instance.getUri().toString());
        apiClient.addAuthorization("ClientCredentials", buildOauthClientCredentialsGrant(appProperties.getKeycloak()));
        return apiClient.buildClient(RestaurantAdminApi.class);
    }

    @Bean
    public RestaurantAdminApiFactory restaurantAdminApiFactory(ApplicationContext applicationContext) {
        return new RestaurantAdminApiFactory(applicationContext);
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
