package dev.thiagooliveira.delivery.restaurants.core.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class Restaurant {
    private UUID id;
    private String name;
    private String description;
    private String phoneNumber;
    private Address address;
}
