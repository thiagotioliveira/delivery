package dev.thiagooliveira.delivery.restaurants.fixtures;

import static dev.thiagooliveira.delivery.restaurants.utils.AddressFormatter.format;

import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetails;
import dev.thiagooliveira.delivery.restaurants.model.*;
import java.util.UUID;

public class Fixtures {

    private static final String RESTAURANT_NAME = "Restaurant A";
    private static final String RESTAURANT_DESCRIPTION = "A great place to eat.";
    private static final String RESTAURANT_PHONE = "+351 210 123 456";
    private static final String RESTAURANT_ADDRESS_STREET = "Rua Augusta";
    private static final String RESTAURANT_ADDRESS_NUMBER = "24";
    private static final String RESTAURANT_ADDRESS_CITY = "Lisbon";
    private static final String RESTAURANT_ADDRESS_NOTES = "";
    private static final String RESTAURANT_ADDRESS_STATE = "Lisbon";
    private static final String RESTAURANT_ADDRESS_POSTAL_CODE = "1100-053";
    private static final String RESTAURANT_ADDRESS_COUNTRY = "Portugal";

    public static dev.thiagooliveira.delivery.restaurants.dto.Restaurant restaurantDto(UUID restaurantId) {
        return new dev.thiagooliveira.delivery.restaurants.dto.Restaurant()
                .id(restaurantId)
                .name(RESTAURANT_NAME)
                .description(RESTAURANT_DESCRIPTION)
                .phoneNumber(RESTAURANT_PHONE)
                .address(addressDto());
    }

    public static Restaurant restaurant(UUID restaurantId) {
        var restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName(RESTAURANT_NAME);
        restaurant.setDescription(RESTAURANT_DESCRIPTION);
        restaurant.setPhoneNumber(RESTAURANT_PHONE);
        restaurant.setAddress(address());
        return restaurant;
    }

    public static RestaurantUser restaurantUser(UUID userId, UUID restaurantId) {
        var restaurant = restaurant(restaurantId);
        var restaurantUser = new RestaurantUser();
        RestaurantUserId restaurantUserId = new RestaurantUserId();
        restaurantUserId.setRestaurantId(restaurant.getId());
        restaurantUserId.setUserId(userId);
        restaurantUser.setId(restaurantUserId);
        restaurantUser.setRestaurant(restaurant);
        restaurantUser.setDistance(0d);
        restaurantUser.setDuration(0d);

        return restaurantUser;
    }

    public static RestaurantIdWithAddress restaurantIdWithAddress(UUID restaurantId) {
        return new RestaurantIdWithAddress(
                restaurantId,
                RESTAURANT_ADDRESS_STREET,
                RESTAURANT_ADDRESS_NUMBER,
                RESTAURANT_ADDRESS_NOTES,
                RESTAURANT_ADDRESS_CITY,
                RESTAURANT_ADDRESS_STATE,
                RESTAURANT_ADDRESS_POSTAL_CODE,
                RESTAURANT_ADDRESS_COUNTRY,
                format(address()),
                0d,
                0d);
    }

    public static dev.thiagooliveira.delivery.restaurants.dto.RestaurantIdWithAddress restaurantIdWithAddressDto(
            UUID restaurantId) {
        return new dev.thiagooliveira.delivery.restaurants.dto.RestaurantIdWithAddress()
                .restaurantId(restaurantId)
                .street(RESTAURANT_ADDRESS_STREET)
                .number(RESTAURANT_ADDRESS_NUMBER)
                .notes(RESTAURANT_ADDRESS_NOTES)
                .city(RESTAURANT_ADDRESS_CITY)
                .state(RESTAURANT_ADDRESS_STATE)
                .postalCode(RESTAURANT_ADDRESS_POSTAL_CODE)
                .country(RESTAURANT_ADDRESS_COUNTRY)
                .formatted(format(address()))
                .latitude(0d)
                .longitude(0d);
    }

    public static dev.thiagooliveira.delivery.restaurants.dto.RestaurantUser restaurantUserDto(
            UUID userId, UUID restaurantId) {
        return new dev.thiagooliveira.delivery.restaurants.dto.RestaurantUser()
                .userId(userId)
                .restaurantId(restaurantId)
                .distance("0")
                .duration("0");
    }

    private static dev.thiagooliveira.delivery.restaurants.dto.Address addressDto() {
        return new dev.thiagooliveira.delivery.restaurants.dto.Address()
                .city(RESTAURANT_ADDRESS_CITY)
                .country(RESTAURANT_ADDRESS_COUNTRY)
                .notes(RESTAURANT_ADDRESS_NOTES)
                .number(RESTAURANT_ADDRESS_NUMBER)
                .state(RESTAURANT_ADDRESS_STATE)
                .postalCode(RESTAURANT_ADDRESS_POSTAL_CODE);
    }

    private static Address address() {
        var address = new Address();
        address.setId(UUID.randomUUID());
        address.setStreet(RESTAURANT_ADDRESS_STREET);
        address.setNumber(RESTAURANT_ADDRESS_NUMBER);
        address.setCity(RESTAURANT_ADDRESS_CITY);
        address.setState(RESTAURANT_ADDRESS_STATE);
        address.setPostalCode(RESTAURANT_ADDRESS_POSTAL_CODE);
        address.setCountry(RESTAURANT_ADDRESS_COUNTRY);
        address.setLatitude(0d);
        address.setLongitude(0d);
        address.setFormatted(format(address));
        return address;
    }

    public static RestaurantUserDetails restaurantUserDetails(UUID restaurantId) {
        return new RestaurantUserDetails()
                .id(restaurantId)
                .name("Restaurant A")
                .description("A great place to eat.")
                .duration("0")
                .distance("0")
                .phoneNumber("+351 210 123 456")
                .address(new dev.thiagooliveira.delivery.restaurants.dto.Address()
                        .city("Lisbon")
                        .country("Portugal")
                        .number("24")
                        .state("Lisbon")
                        .postalCode("1100-053")
                        .notes("")
                        .street("Rua Augusta"));
    }
}
