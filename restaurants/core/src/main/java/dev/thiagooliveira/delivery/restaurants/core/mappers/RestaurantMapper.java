package dev.thiagooliveira.delivery.restaurants.core.mappers;

import dev.thiagooliveira.delivery.restaurants.core.model.CreateRestaurant;
import dev.thiagooliveira.delivery.restaurants.core.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.core.model.RestaurantSimple;
import dev.thiagooliveira.delivery.restaurants.core.model.UpdateRestaurant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface RestaurantMapper {
    Restaurant toRestaurant(CreateRestaurant createRestaurant);

    Restaurant merge(@MappingTarget Restaurant restaurant, UpdateRestaurant updateRestaurant);

    RestaurantSimple toRestaurantSimple(Restaurant restaurant, Double distanceInMeters);
}
