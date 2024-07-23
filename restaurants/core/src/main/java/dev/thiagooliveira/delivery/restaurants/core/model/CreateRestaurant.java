package dev.thiagooliveira.delivery.restaurants.core.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateRestaurant {
    @NotEmpty
    private String name;

    private String description;

    @NotEmpty
    private String phoneNumber;

    @NotNull
    private Address address;
}
