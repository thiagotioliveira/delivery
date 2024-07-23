package dev.thiagooliveira.delivery.restaurants.core.services;

import dev.thiagooliveira.delivery.restaurants.core.exceptions.CreateRestaurantException;
import dev.thiagooliveira.delivery.restaurants.core.exceptions.RestaurantNotFoundException;
import dev.thiagooliveira.delivery.restaurants.core.exceptions.UpdateRestaurantException;
import dev.thiagooliveira.delivery.restaurants.core.mappers.RestaurantMapper;
import dev.thiagooliveira.delivery.restaurants.core.model.*;
import dev.thiagooliveira.delivery.restaurants.core.repositories.RestaurantRepository;
import dev.thiagooliveira.delivery.restaurants.core.repositories.RestaurantUserDistanceRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final Validator validator;
    private final RestaurantMapper mapper;
    private final RestaurantRepository repository;
    private final RestaurantUserDistanceRepository restaurantUserDistanceRepository;

    @Override
    public Restaurant create(CreateRestaurant createRestaurant) {
        Set<ConstraintViolation<CreateRestaurant>> violations = validator.validate(createRestaurant);
        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(mapConstraintViolationStringFunction())
                    .collect(Collectors.toList());
            throw new CreateRestaurantException(errorMessages);
        }
        Restaurant restaurant = mapper.toRestaurant(createRestaurant);
        return repository.save(restaurant);
    }

    @Override
    public Restaurant update(UUID id, UpdateRestaurant updateRestaurant) {
        Set<ConstraintViolation<UpdateRestaurant>> violations = validator.validate(updateRestaurant);
        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(mapConstraintViolationStringFunction())
                    .collect(Collectors.toList());
            throw new UpdateRestaurantException(errorMessages);
        }
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
        return repository.save(mapper.merge(restaurant, updateRestaurant));
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new RestaurantNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<RestaurantSimple> get(UUID restaurantId, UUID userId) {
        Optional<Restaurant> optional = repository.findById(restaurantId);
        if (optional.isPresent()) {
            var distance = restaurantUserDistanceRepository
                    .getDistance(restaurantId, userId)
                    .orElse(-1d);
            return Optional.of(mapper.toRestaurantSimple(optional.get(), distance));
        }
        return Optional.empty();
    }

    @Override
    public Page<RestaurantSimple> list(UUID userId, double maxDistance, PageRequest pageRequest) {
        return repository.findByUserIdAndMaxDistance(userId, maxDistance, pageRequest);
    }

    private static Function<ConstraintViolation<?>, String> mapConstraintViolationStringFunction() {
        return violation -> violation.getPropertyPath() + ": " + violation.getMessage();
    }
}
