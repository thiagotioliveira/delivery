package dev.thiagooliveira.delivery.users.config;

import dev.thiagooliveira.delivery.users.config.properties.AppProperties;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import dev.thiagooliveira.delivery.users.services.IAMService;
import dev.thiagooliveira.delivery.users.services.KeycloakService;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IAMConfig {

    @Bean
    public IAMService iamService(AppProperties appProperties, UserMapper userMapper) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(appProperties.getKeycloak().getBaseUrl())
                .realm(appProperties.getKeycloak().getRealm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(appProperties.getKeycloak().getClientId())
                .clientSecret(appProperties.getKeycloak().getClientSecret())
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(appProperties.getKeycloak().getConnectionPoolSize())
                        .build())
                .build();
        RealmResource realm = keycloak.realm(appProperties.getKeycloak().getRealm());
        return new KeycloakService(realm, userMapper);
    }
}
