package dev.thiagooliveira.delivery.restaurants.repositories;

import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantIdWithAddressProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends CrudRepository<Restaurant, UUID> {

    @Query("SELECT new dev.thiagooliveira.delivery.restaurants.model.RestaurantIdWithAddressProjection("
            + "r.id, a.street, a.number, a.notes, a.city, a.state, a.postalCode, a.country) "
            + "FROM Restaurant r JOIN r.address a "
            + "WHERE a.state = :state AND a.country = :country")
    List<RestaurantIdWithAddressProjection> findByAddressStateAndAddressCountry(
            @Param("state") String state, @Param("country") String country);
}
