package dev.thiagooliveira.delivery.restaurants.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String street;
    private String number;
    private String city;

    @NotNull
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @NotNull
    private String country;

    private String notes;
}
