package dev.thiagooliveira.delivery.ui.config.properties;

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

    @Getter
    @Setter
    public static class Client {
        private Service gateway;

        @Getter
        @Setter
        public static class Service {
            private String baseUrl;
        }
    }
}
