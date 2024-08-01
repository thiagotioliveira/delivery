package dev.thiagooliveira.delivery.orders.config;

import dev.thiagooliveira.delivery.orders.config.properties.AppProperties;
import dev.thiagooliveira.delivery.restaurants.clients.invokers.auth.OauthClientCredentialsGrant;
import dev.thiagooliveira.delivery.users.clients.UsersAdminApi;
import dev.thiagooliveira.delivery.users.clients.invokers.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersApiConfig {

    @Bean
    public UsersAdminApi userApi(AppProperties appProperties) {
        ApiClient apiClient = new ApiClient()
                .setBasePath(appProperties.getClient().getUsersService().getBaseUrl());
        apiClient.addAuthorization("ClientCredentials", buildOauthClientCredentialsGrant(appProperties.getKeycloak()));
        return apiClient.buildClient(UsersAdminApi.class);
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
