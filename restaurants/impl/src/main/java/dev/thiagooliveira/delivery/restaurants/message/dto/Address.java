package dev.thiagooliveira.delivery.restaurants.message.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
    private UUID id;
    private String street;
    private String number;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String notes;
    private String formatted;
    private Double latitude;
    private Double longitude;
}
