package dev.thiagooliveira.delivery.restaurants.service;

import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantIdWithAddressProjection;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantUser;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantRepository;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantUserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantUserRepository restaurantUserRepository;

    @Override
    public long count() {
        return restaurantRepository.count();
    }

    @Override
    public void deleteRestaurantsByUserId(UUID userId) {
        restaurantUserRepository.deleteByIdUserId(userId);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantUser save(RestaurantUser restaurantUser) {
        return restaurantUserRepository.save(restaurantUser);
    }

    @Override
    public List<RestaurantIdWithAddressProjection> findByAddressStateAndAddressCountry(String state, String country) {
        return restaurantRepository.findByAddressStateAndAddressCountry(state, country);
    }
}
