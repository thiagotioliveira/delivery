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
    private Client client;
    private Keycloak keycloak;

    @Getter
    @Setter
    public static class Client {
        private Service restaurantsService;

        @Getter
        @Setter
        public static class Service {
            private String serviceId;
        }
    }

    @Getter
    @Setter
    public static class Keycloak {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private String realm;
    }
}
