package dev.thiagooliveira.delivery.restaurants.mappers;

import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantIdWithAddress;
import dev.thiagooliveira.delivery.restaurants.message.dto.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    Address toAddress(RestaurantIdWithAddress restaurantIdWithAddress);

    Address toAddress(dev.thiagooliveira.delivery.users.message.dto.Address address);

    AddressValidated toAddressValidated(dev.thiagooliveira.delivery.restaurants.message.dto.Address address);
}
