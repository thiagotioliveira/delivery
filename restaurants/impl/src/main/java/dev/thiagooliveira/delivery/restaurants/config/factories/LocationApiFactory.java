package dev.thiagooliveira.delivery.restaurants.config.factories;

import dev.thiagooliveira.delivery.location.clients.LocationApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class LocationApiFactory {

    private final ApplicationContext applicationContext;

    public LocationApi create() {
        return applicationContext.getBean(LocationApi.class);
    }
}
