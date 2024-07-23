package dev.thiagooliveira.delivery.restaurants.core.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Address {

    @NotEmpty
    private String street;

    private String number;

    @NotEmpty
    private String city;

    private String state;

    @NotEmpty
    private String postalCode;

    @NotEmpty
    private String country;
}
