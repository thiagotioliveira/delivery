package dev.thiagooliveira.delivery.restaurants.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantUserId {
    @NotNull
    private UUID restaurantId;

    @NotNull
    private UUID userId;
}
