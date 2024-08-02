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

    @NotNull
    private String street;

    @NotNull
    private String number;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @NotNull
    private String country;

    private String notes;

    @NotNull
    private String formatted;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
