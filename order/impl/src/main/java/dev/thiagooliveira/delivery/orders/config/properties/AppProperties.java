package dev.thiagooliveira.delivery.orders.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {
    private Gateway gateway;
    private Client client;
    private Keycloak keycloak;

    @Getter
    @Setter
    public static class Gateway {
        private String baseUrl;
    }

    @Getter
    @Setter
    public static class Client {
        private Service usersService;
        private Service restaurantsService;
        private Service menusService;

        @Getter
        @Setter
        public static class Service {
            private String baseUrl;
        }
    }

    @Getter
    @Setter
    public static class Keycloak {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private String realm;
        private int connectionPoolSize;
    }
}
