package dev.thiagooliveira.delivery.restaurants.core.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class RestaurantUserDistance {
    @NotNull
    private Restaurant restaurant;

    @NotNull
    private UUID userId;

    @NotNull
    private Double distanceInMeters;
}
