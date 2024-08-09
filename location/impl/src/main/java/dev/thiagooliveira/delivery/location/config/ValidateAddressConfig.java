package dev.thiagooliveira.delivery.location.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import com.google.maps.addressvalidation.v1.AddressValidationClient;
import dev.thiagooliveira.delivery.location.config.factories.GoogleValidateClientFactory;
import dev.thiagooliveira.delivery.location.exceptions.AddressValidationException;
import dev.thiagooliveira.delivery.location.services.ValidateAddressService;
import dev.thiagooliveira.delivery.location.services.ValidateAddressServiceImpl;
import dev.thiagooliveira.delivery.location.services.google.GoogleValidateAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class ValidateAddressConfig {
    @Bean
    public ValidateAddressService validateAddressClient(
            @Value("${google.application.credentials:null}") String credentialsPath,
            GoogleValidateClientFactory googleValidateClientFactory) {
        if ("null".equals(credentialsPath) || credentialsPath == null) {
            log.debug("using ValidateAddressServiceImpl");
            return new ValidateAddressServiceImpl();
        } else {
            log.debug("using GoogleValidateAddressService");
            return new GoogleValidateAddressService(googleValidateClientFactory);
        }
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public AddressValidationClient addressValidationClient() {
        try {
            return AddressValidationClient.create();
        } catch (Exception e) {
            log.error("some error when try to validate the address.", e);
            throw new AddressValidationException();
        }
    }

    @Bean
    public GoogleValidateClientFactory googleValidateClientFactory(ApplicationContext applicationContext) {
        return new GoogleValidateClientFactory(applicationContext);
    }
}
