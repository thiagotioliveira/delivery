package dev.thiagooliveira.delivery.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    @NotNull
    private String street;

    private String number;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String postalCode;

    @NotNull
    private String country;

    private String notes;
}
