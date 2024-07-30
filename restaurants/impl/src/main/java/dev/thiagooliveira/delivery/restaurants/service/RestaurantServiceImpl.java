package dev.thiagooliveira.delivery.restaurants.service;

import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantPage;
import dev.thiagooliveira.delivery.restaurants.mappers.RestaurantMapper;
import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantIdWithAddressProjection;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantUser;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantRepository;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantUserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantUserRepository restaurantUserRepository;

    @Override
    public RestaurantPage getAll(PageRequest pageRequest) {
        return restaurantMapper.toRestaurantPage(
                restaurantRepository.findAll(org.springframework.data.domain.PageRequest.of(
                        pageRequest.getPageNumber(), pageRequest.getPageSize())));
    }

    @Override
    public Optional<Restaurant> getById(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId);
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
