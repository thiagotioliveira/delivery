package dev.thiagooliveira.delivery.restaurants.repositories;

import dev.thiagooliveira.delivery.restaurants.model.RestaurantUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantUserRepository extends PagingAndSortingRepository<RestaurantUser, UUID> {

    void deleteByIdUserId(UUID userId);

    Page<RestaurantUser> findByIdUserId(UUID userId, Pageable pageable);

    Optional<RestaurantUser> findByIdRestaurantIdAndIdUserId(UUID restaurantId, UUID userId);

    RestaurantUser save(RestaurantUser restaurantUser);
}
