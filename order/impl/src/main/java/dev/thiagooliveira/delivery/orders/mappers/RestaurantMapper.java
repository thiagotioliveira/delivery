package dev.thiagooliveira.delivery.orders.mappers;

import dev.thiagooliveira.delivery.orders.spec.dto.Restaurant;
import org.mapstruct.Mapper;

@Mapper
public interface RestaurantMapper {

    Restaurant toRestaurant(dev.thiagooliveira.delivery.restaurants.spec.dto.Restaurant restaurant);
}
