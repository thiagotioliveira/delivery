package dev.thiagooliveira.delivery.restaurants.repositories;

import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantIdWithAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, UUID> {

    @Query("SELECT new dev.thiagooliveira.delivery.restaurants.model.RestaurantIdWithAddress("
            + "r.id, a.street, a.number, a.notes, a.city, a.state, a.postalCode, a.country) "
            + "FROM Restaurant r JOIN r.address a "
            + "WHERE a.state = :state AND a.country = :country")
    List<RestaurantIdWithAddress> findByAddressStateAndAddressCountry(
            @Param("state") String state, @Param("country") String country);

    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(UUID id);
}
