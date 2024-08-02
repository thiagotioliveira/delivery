package dev.thiagooliveira.delivery.users.message.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Address implements Serializable {
    private String street;
    private String number;
    private String notes;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String formatted;
    private Double latitude;
    private Double longitude;
}
