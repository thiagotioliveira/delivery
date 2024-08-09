package dev.thiagooliveira.delivery.location.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.delivery.location.config.factories.RandomFactory;
import dev.thiagooliveira.delivery.location.dto.Route;
import dev.thiagooliveira.delivery.location.fixtures.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DirectionServiceImplTest {

    @Mock
    private RandomFactory randomFactory;

    private DirectionService directionService;

    @BeforeEach
    void setUp() {
        this.directionService = new DirectionServiceImpl(randomFactory);
    }

    @Test
    @DisplayName("calculates the route between two addresses.")
    void directionTest() {
        when(randomFactory.create()).thenReturn(0.22d).thenReturn(0.19d);
        Route route = this.directionService.direction(Fixtures.from(), Fixtures.to());
        assertEquals("2.2 km", route.getDistance());
        assertEquals("15 mins", route.getDuration());
    }
}
