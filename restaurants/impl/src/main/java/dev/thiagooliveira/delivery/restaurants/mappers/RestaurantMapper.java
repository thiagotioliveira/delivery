package dev.thiagooliveira.delivery.restaurants.mappers;

import dev.thiagooliveira.delivery.restaurants.dto.Restaurant;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantPage;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantIdWithAddress;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantUser;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@Mapper
public interface RestaurantMapper {
    Restaurant toRestaurant(dev.thiagooliveira.delivery.restaurants.model.Restaurant restaurant);

    dev.thiagooliveira.delivery.restaurants.model.Restaurant toRestaurant(Restaurant restaurant);

    RestaurantPage toRestaurantPage(
            org.springframework.data.domain.Page<dev.thiagooliveira.delivery.restaurants.model.Restaurant> page);

    @Mapping(target = "id.restaurantId", source = "restaurantId")
    @Mapping(target = "id.userId", source = "userId")
    @Mapping(target = "restaurant", expression = "java(buildNewRestaurantWithId(restaurantUser.getRestaurantId()))")
    RestaurantUser toRestaurantUser(dev.thiagooliveira.delivery.restaurants.dto.RestaurantUser restaurantUser);

    @Mapping(target = "restaurantId", source = "id.restaurantId")
    @Mapping(target = "userId", source = "id.userId")
    dev.thiagooliveira.delivery.restaurants.dto.RestaurantUser toRestaurantUser(RestaurantUser restaurantUser);

    dev.thiagooliveira.delivery.restaurants.dto.RestaurantIdWithAddress toRestaurantIdWithAddress(
            RestaurantIdWithAddress restaurantIdWithAddress);

    default dev.thiagooliveira.delivery.restaurants.model.Restaurant buildNewRestaurantWithId(UUID restaurantId) {
        dev.thiagooliveira.delivery.restaurants.model.Restaurant restaurant =
                new dev.thiagooliveira.delivery.restaurants.model.Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }

    default PageImpl map(RestaurantPage page) {
        return new PageImpl<>(
                page.getContent(),
                PageRequest.of(
                        page.getPageable().getPageNumber(), page.getPageable().getPageSize()),
                page.getTotalElements());
    }
}
