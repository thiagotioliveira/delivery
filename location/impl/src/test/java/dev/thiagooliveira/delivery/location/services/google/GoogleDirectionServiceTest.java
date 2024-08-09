package dev.thiagooliveira.delivery.location.services.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import dev.thiagooliveira.delivery.location.config.factories.GoogleDirectionApiFactory;
import dev.thiagooliveira.delivery.location.dto.Route;
import dev.thiagooliveira.delivery.location.exceptions.DirectionException;
import dev.thiagooliveira.delivery.location.fixtures.Fixtures;
import dev.thiagooliveira.delivery.location.services.DirectionService;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GoogleDirectionServiceTest {
    @Mock
    private GoogleDirectionApiFactory googleDirectionApiFactory;

    private DirectionService googleDirectionService;

    @BeforeEach
    void setUp() {
        this.googleDirectionService = new GoogleDirectionService(googleDirectionApiFactory);
    }

    @Test
    @DisplayName("calculates the route between two addresses.")
    void directionTest() throws IOException, InterruptedException, ApiException {
        Distance distance = new Distance();
        distance.humanReadable = "9.7 km";
        distance.inMeters = 9700L;

        Duration duration = new Duration();
        duration.humanReadable = "53 mins";
        duration.inSeconds = 3180L;

        DirectionsLeg leg = new DirectionsLeg();
        leg.distance = distance;
        leg.duration = duration;

        DirectionsRoute directionsRoute = new DirectionsRoute();
        directionsRoute.legs = new DirectionsLeg[] {leg};

        DirectionsResult result = new DirectionsResult();
        result.routes = new DirectionsRoute[] {directionsRoute};

        DirectionsApiRequest directionsApiRequest = mock(DirectionsApiRequest.class);
        when(directionsApiRequest.await()).thenReturn(result);
        when(googleDirectionApiFactory.create()).thenReturn(directionsApiRequest);

        Route route = this.googleDirectionService.direction(Fixtures.from(), Fixtures.to());
        assertEquals("9.7 km", route.getDistance());
        assertEquals("53 mins", route.getDuration());
    }

    @Test
    @DisplayName("throws exception when try to calculates the route between two addresses.")
    void directionWithError() throws IOException, InterruptedException, ApiException {
        DirectionsApiRequest directionsApiRequest = mock(DirectionsApiRequest.class);
        when(googleDirectionApiFactory.create()).thenReturn(directionsApiRequest);
        when(directionsApiRequest.await()).thenThrow(ApiException.class);
        assertThrows(DirectionException.class, () -> {
            this.googleDirectionService.direction(Fixtures.from(), Fixtures.to());
        });
    }
}
