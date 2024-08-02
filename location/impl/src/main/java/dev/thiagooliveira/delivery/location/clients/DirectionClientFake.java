package dev.thiagooliveira.delivery.location.clients;

import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Route;
import java.util.Random;

public class DirectionClientFake implements DirectionClient {
    @Override
    public Route direction(AddressValidated from, AddressValidated to) {
        Random random = new Random();
        // Generate a random value for distance between 0 and 10
        double distance = 10 * random.nextDouble();
        // Generate a random factor for duration between 5 and 15 (minutes per km)
        double factor = 5 + (10 * random.nextDouble());
        // Calculate duration based on distance and the factor
        double duration = distance * factor;
        return new Route().distance(distance + "km").duration(duration + "min");
    }
}
