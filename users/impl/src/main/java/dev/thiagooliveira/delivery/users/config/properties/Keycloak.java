package dev.thiagooliveira.delivery.users.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Keycloak {
    private String baseUrl;
    private String clientId;
    private String clientSecret;
    private String realm;
    private int connectionPoolSize;
}
