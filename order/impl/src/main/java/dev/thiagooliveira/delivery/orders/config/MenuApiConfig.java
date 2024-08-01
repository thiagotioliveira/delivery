package dev.thiagooliveira.delivery.orders.config;

import dev.thiagooliveira.delivery.menus.clients.MenuApi;
import dev.thiagooliveira.delivery.menus.clients.invokers.ApiClient;
import dev.thiagooliveira.delivery.menus.clients.invokers.auth.OauthClientCredentialsGrant;
import dev.thiagooliveira.delivery.orders.config.properties.AppProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuApiConfig {

    @Bean
    public MenuApi menuApi(AppProperties appProperties) {
        ApiClient apiClient = new ApiClient()
                .setBasePath(appProperties.getClient().getMenusService().getBaseUrl());
        apiClient.addAuthorization("ClientCredentials", buildOauthClientCredentialsGrant(appProperties.getKeycloak()));
        return apiClient.buildClient(MenuApi.class);
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
