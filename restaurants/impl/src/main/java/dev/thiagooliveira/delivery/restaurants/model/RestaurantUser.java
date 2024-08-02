package dev.thiagooliveira.delivery.restaurants.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class RestaurantUser {
    @EmbeddedId
    private RestaurantUserId id;

    @ManyToOne
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NotNull
    private String distance;

    @NotNull
    private String duration;
}
