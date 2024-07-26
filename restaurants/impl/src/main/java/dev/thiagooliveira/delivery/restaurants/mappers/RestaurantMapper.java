package dev.thiagooliveira.delivery.restaurants.mappers;

import dev.thiagooliveira.delivery.restaurants.spec.dto.Restaurant;
import dev.thiagooliveira.delivery.restaurants.spec.dto.RestaurantPage;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@Mapper
public interface RestaurantMapper {
    Restaurant toRestaurant(dev.thiagooliveira.delivery.restaurants.model.Restaurant restaurant);

    RestaurantPage toRestaurantPage(
            org.springframework.data.domain.Page<dev.thiagooliveira.delivery.restaurants.model.Restaurant> page);

    default PageImpl map(RestaurantPage page) {
        return new PageImpl<>(
                page.getContent(),
                PageRequest.of(
                        page.getPageable().getPageNumber(), page.getPageable().getPageSize()),
                page.getTotalElements());
    }
}
