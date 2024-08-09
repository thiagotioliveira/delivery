package dev.thiagooliveira.delivery.location.services;

import dev.thiagooliveira.delivery.location.config.factories.RandomFactory;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Route;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectionServiceImpl implements DirectionService {
    private final RandomFactory randomFactory;

    @Override
    public Route direction(AddressValidated from, AddressValidated to) {
        // Generate a random value for distance between 0 and 10
        BigDecimal distance = BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(randomFactory.create()));
        distance = distance.setScale(1, RoundingMode.HALF_UP);
        // Generate a random factor for duration between 5 and 15 (minutes per km)
        BigDecimal factor =
                BigDecimal.valueOf(5).add(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(randomFactory.create())));
        // Calculate duration based on distance and the factor
        BigDecimal duration = distance.multiply(factor).setScale(0, RoundingMode.HALF_UP);
        return new Route().distance(distance.toString() + " km").duration(duration.toString() + " mins");
    }
}
