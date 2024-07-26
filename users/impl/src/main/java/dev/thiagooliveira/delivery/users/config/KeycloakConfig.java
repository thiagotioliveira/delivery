package dev.thiagooliveira.delivery.users.config;

import dev.thiagooliveira.delivery.users.clients.IAMClient;
import dev.thiagooliveira.delivery.users.clients.KeycloakClient;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public IAMClient keycloakClient(
            @Value("${keycloak.serverUrl}") String serverUrl,
            @Value("${keycloak.userRealm}") String userRealm,
            @Value("${keycloak.clientId}") String clientId,
            @Value("${keycloak.clientSecret}") String clientSecret,
            @Value("${keycloak.connectionPoolSize}") int connectionPoolSize,
            UserMapper userMapper) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(userRealm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(connectionPoolSize)
                        .build())
                .build();
        RealmResource realm = keycloak.realm(userRealm);
        return new KeycloakClient(realm, userMapper);
    }
}
