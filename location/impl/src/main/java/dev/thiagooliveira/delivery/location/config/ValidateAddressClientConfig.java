package dev.thiagooliveira.delivery.location.config;

import dev.thiagooliveira.delivery.location.clients.ValidateAddressClient;
import dev.thiagooliveira.delivery.location.clients.ValidateAddressClientFake;
import dev.thiagooliveira.delivery.location.clients.ValidateAddressClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ValidateAddressClientConfig {
    @Bean
    public ValidateAddressClient validateAddressClient(
            @Value("${google.application.credentials:null}") String credentialsPath) {
        if ("null".equals(credentialsPath) || credentialsPath == null) {
            log.debug("using ValidateAddressClientFake");
            return new ValidateAddressClientFake();
        } else {
            log.debug("using ValidateAddressClientImpl");
            return new ValidateAddressClientImpl();
        }
    }
}
