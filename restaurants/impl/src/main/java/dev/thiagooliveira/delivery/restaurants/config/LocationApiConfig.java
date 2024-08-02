package dev.thiagooliveira.delivery.restaurants.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import dev.thiagooliveira.delivery.location.clients.LocationApi;
import dev.thiagooliveira.delivery.location.clients.invokers.ApiClient;
import dev.thiagooliveira.delivery.location.clients.invokers.auth.OauthClientCredentialsGrant;
import dev.thiagooliveira.delivery.restaurants.config.factories.LocationApiFactory;
import dev.thiagooliveira.delivery.restaurants.config.properties.AppProperties;
import dev.thiagooliveira.delivery.restaurants.exceptions.ServiceInstanceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class LocationApiConfig {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public LocationApi locationApi(AppProperties appProperties, LoadBalancerClient loadBalancerClient) {
        ServiceInstance instance = loadBalancerClient.choose(
                appProperties.getClient().getLocationService().getServiceId());
        if (instance == null) {
            throw new ServiceInstanceNotFoundException(String.format(
                    "%s not found.",
                    appProperties.getClient().getLocationService().getServiceId()));
        }
        ApiClient apiClient = new ApiClient().setBasePath(instance.getUri().toString());
        apiClient.addAuthorization("ClientCredentials", buildOauthClientCredentialsGrant(appProperties.getKeycloak()));
        LocationApi locationApi = apiClient.buildClient(LocationApi.class);
        log.debug("creating locationApi - prototype scope.");
        return locationApi;
    }

    @Bean
    public LocationApiFactory locationApiFactory(ApplicationContext applicationContext) {
        return new LocationApiFactory(applicationContext);
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
