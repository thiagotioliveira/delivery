package dev.thiagooliveira.delivery.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Restaurant {

    @Column(name = "restaurant_id")
    private UUID id;

    @NotNull
    @Column(name = "restaurant_name")
    private String name;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "restaurant_street")),
        @AttributeOverride(name = "number", column = @Column(name = "restaurant_number")),
        @AttributeOverride(name = "city", column = @Column(name = "restaurant_city")),
        @AttributeOverride(name = "state", column = @Column(name = "restaurant_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "restaurant_postal_code")),
        @AttributeOverride(name = "country", column = @Column(name = "restaurant_country")),
        @AttributeOverride(name = "notes", column = @Column(name = "restaurant_notes"))
    })
    private Address address;
}
