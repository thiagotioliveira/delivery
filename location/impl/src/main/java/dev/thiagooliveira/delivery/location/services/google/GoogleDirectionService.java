package dev.thiagooliveira.delivery.location.services.google;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import dev.thiagooliveira.delivery.location.config.factories.GoogleDirectionApiFactory;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Route;
import dev.thiagooliveira.delivery.location.exceptions.DirectionException;
import dev.thiagooliveira.delivery.location.services.DirectionService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GoogleDirectionService implements DirectionService {

    private final GoogleDirectionApiFactory googleDirectionApiFactory;

    @Override
    public Route direction(AddressValidated from, AddressValidated to) {
        try {
            DirectionsApiRequest request = googleDirectionApiFactory.create();
            request.origin(from.getFormatted());
            request.destination(to.getFormatted());
            DirectionsResult result = request.await();
            DirectionsLeg directionsLeg =
                    Arrays.stream(result.routes).map(r -> r.legs[0]).findFirst().orElseThrow();
            return new Route().distance(directionsLeg.distance.toString()).duration(directionsLeg.duration.toString());
        } catch (Exception e) {
            log.error("some error when try to get direction.", e);
            throw new DirectionException();
        }
    }
}
