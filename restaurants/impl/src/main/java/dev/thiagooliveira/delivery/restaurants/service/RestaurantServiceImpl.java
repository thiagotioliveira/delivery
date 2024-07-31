package dev.thiagooliveira.delivery.restaurants.service;

import dev.thiagooliveira.delivery.restaurants.dto.*;
import dev.thiagooliveira.delivery.restaurants.mappers.RestaurantMapper;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantRepository;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantUserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
        return restaurantRepository.findById(restaurantId).map(restaurantMapper::toRestaurant);
    }

    @Override
    public void deleteRestaurantsByUserId(UUID userId) {
        restaurantUserRepository.deleteByIdUserId(userId);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantMapper.toRestaurant(restaurantRepository.save(restaurantMapper.toRestaurant(restaurant)));
    }

    @Override
    public RestaurantUser save(RestaurantUser restaurantUser) {
        return restaurantMapper.toRestaurantUser(
                restaurantUserRepository.save(restaurantMapper.toRestaurantUser(restaurantUser)));
    }

    @Override
    public List<RestaurantIdWithAddress> getByAddressStateAndAddressCountry(String state, String country) {
        return restaurantRepository.findByAddressStateAndAddressCountry(state, country).stream()
                .map(restaurantMapper::toRestaurantIdWithAddress)
                .collect(Collectors.toList());
    }
}
