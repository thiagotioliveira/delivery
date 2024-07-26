package dev.thiagooliveira.delivery.restaurants.message.dto;

import dev.thiagooliveira.delivery.restaurants.model.Address;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CreateRestaurantUserCommand implements Serializable {
    @NotNull
    private UUID restaurantId;

    @NotNull
    private Address restaurantAddress;

    @NotNull
    private UUID userId;

    @NotNull
    private Address userAddress;
}
