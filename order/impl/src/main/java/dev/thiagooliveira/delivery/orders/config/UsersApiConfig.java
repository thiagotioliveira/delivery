package dev.thiagooliveira.delivery.orders.config;

import dev.thiagooliveira.delivery.orders.config.factories.UsersApiFactory;
import dev.thiagooliveira.delivery.orders.config.properties.AppProperties;
import dev.thiagooliveira.delivery.orders.exceptions.ServiceInstanceNotFoundException;
import dev.thiagooliveira.delivery.restaurants.clients.invokers.auth.OauthClientCredentialsGrant;
import dev.thiagooliveira.delivery.users.clients.UsersAdminApi;
import dev.thiagooliveira.delivery.users.clients.invokers.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
@Slf4j
public class UsersApiConfig {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public UsersAdminApi userApi(AppProperties appProperties, LoadBalancerClient loadBalancerClient) {
        ServiceInstance instance = loadBalancerClient.choose(appProperties.getClient().getUsersService().getServiceId());
        if(instance == null){
            throw new ServiceInstanceNotFoundException(appProperties.getClient().getUsersService().getServiceId());
        }
        ApiClient apiClient = new ApiClient()
                .setBasePath(instance.getUri().toString());
        apiClient.addAuthorization("ClientCredentials", buildOauthClientCredentialsGrant(appProperties.getKeycloak()));
        UsersAdminApi usersAdminApi = apiClient.buildClient(UsersAdminApi.class);
        log.debug("creating usersAdminApi - prototype scope.");
        return usersAdminApi;
    }

    @Bean
    public UsersApiFactory usersApiFactory(ApplicationContext applicationContext){
        return new UsersApiFactory(applicationContext);
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
