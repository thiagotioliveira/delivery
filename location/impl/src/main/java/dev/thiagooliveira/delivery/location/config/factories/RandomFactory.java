package dev.thiagooliveira.delivery.location.config.factories;

import java.util.Random;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomFactory {

    private final Random random;

    public Double create() {
        return random.nextDouble();
    }
}
