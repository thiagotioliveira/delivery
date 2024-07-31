package dev.thiagooliveira.delivery.restaurants.model;

import jakarta.persistence.Column;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RestaurantIdWithAddress {
    private UUID restaurantId;
    private String street;
    private String number;
    private String notes;
    private String city;
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    private String country;
}
