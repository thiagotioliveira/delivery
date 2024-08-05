package dev.thiagooliveira.delivery.orders.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.*;

import dev.thiagooliveira.delivery.menus.clients.MenuApi;
import dev.thiagooliveira.delivery.menus.clients.invokers.ApiClient;
import dev.thiagooliveira.delivery.menus.clients.invokers.auth.OauthClientCredentialsGrant;
import dev.thiagooliveira.delivery.orders.config.factories.MenuApiFactory;
import dev.thiagooliveira.delivery.orders.config.properties.AppProperties;
import dev.thiagooliveira.delivery.orders.exceptions.ServiceInstanceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class MenuApiConfig {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public MenuApi menuApi(AppProperties appProperties, LoadBalancerClient loadBalancerClient) {
        ServiceInstance instance = loadBalancerClient.choose(
                appProperties.getClient().getMenusService().getServiceId());
        if (instance == null) {
            throw new ServiceInstanceNotFoundException(
                    appProperties.getClient().getMenusService().getServiceId());
        }
        ApiClient apiClient = new ApiClient().setBasePath(instance.getUri().toString());
        apiClient.addAuthorization("ClientCredentials", buildOauthClientCredentialsGrant(appProperties.getKeycloak()));
        MenuApi menuApi = apiClient.buildClient(MenuApi.class);
        log.debug("creating menuApi - prototype scope.");
        return menuApi;
    }

    @Bean
    public MenuApiFactory menuApiFactory(ApplicationContext applicationContext) {
        return new MenuApiFactory(applicationContext);
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
