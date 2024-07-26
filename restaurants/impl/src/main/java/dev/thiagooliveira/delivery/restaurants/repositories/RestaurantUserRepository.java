package dev.thiagooliveira.delivery.restaurants.repositories;

import dev.thiagooliveira.delivery.restaurants.model.RestaurantUser;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantUserRepository extends CrudRepository<RestaurantUser, UUID> {

    void deleteByIdUserId(UUID userId);
}
