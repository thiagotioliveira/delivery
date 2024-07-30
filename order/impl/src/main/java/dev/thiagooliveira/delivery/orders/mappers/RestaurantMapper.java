package dev.thiagooliveira.delivery.orders.mappers;

import dev.thiagooliveira.delivery.orders.dto.Restaurant;
import org.mapstruct.Mapper;

@Mapper
public interface RestaurantMapper {

    Restaurant toRestaurant(dev.thiagooliveira.delivery.restaurants.dto.Restaurant restaurant);
}
