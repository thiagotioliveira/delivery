package dev.thiagooliveira.delivery.restaurants.config.properties;

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
    private Client client;

    @Getter
    @Setter
    public static class Keycloak {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private String realm;
    }

    @Getter
    @Setter
    public static class Client {
        private Service locationService;

        @Getter
        @Setter
        public static class Service {
            private String serviceId;
        }
    }
}
