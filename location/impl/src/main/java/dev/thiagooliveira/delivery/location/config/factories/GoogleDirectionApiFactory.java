package dev.thiagooliveira.delivery.location.config.factories;

import com.google.maps.DirectionsApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class GoogleDirectionApiFactory {

    private final ApplicationContext applicationContext;

    public DirectionsApiRequest create() {
        return applicationContext.getBean(DirectionsApiRequest.class);
    }
}
