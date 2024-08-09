package dev.thiagooliveira.delivery.location.config.factories;

import com.google.maps.addressvalidation.v1.AddressValidationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class GoogleValidateClientFactory {

    private final ApplicationContext applicationContext;

    public AddressValidationClient create() {
        return applicationContext.getBean(AddressValidationClient.class);
    }
}
