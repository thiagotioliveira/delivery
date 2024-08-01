package dev.thiagooliveira.delivery.menus.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {
    private Keycloak keycloak;

    @Getter
    @Setter
    public static class Keycloak {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private String realm;
    }
}
