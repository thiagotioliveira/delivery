package dev.thiagooliveira.delivery.location.config;

import dev.thiagooliveira.delivery.location.clients.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DirectionClientConfig {
    @Bean
    public DirectionClient directionClient(@Value("${app.google.directionsApi.apiKey:null}") String apiKey) {
        if ("null".equals(apiKey) || apiKey == null) {
            log.debug("using DirectionClientFake");
            return new DirectionClientFake();
        } else {
            log.debug("using GoogleDirectionClient");
            return new GoogleDirectionClient(apiKey);
        }
    }
}
