package dev.thiagooliveira.delivery.orders.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class User {

    @Column(name = "user_id")
    private UUID id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "user_street")),
        @AttributeOverride(name = "number", column = @Column(name = "user_number")),
        @AttributeOverride(name = "city", column = @Column(name = "user_city")),
        @AttributeOverride(name = "state", column = @Column(name = "user_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "user_postal_code")),
        @AttributeOverride(name = "country", column = @Column(name = "user_country")),
        @AttributeOverride(name = "notes", column = @Column(name = "user_notes"))
    })
    private Address address;
}
