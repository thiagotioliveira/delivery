package dev.thiagooliveira.delivery.restaurants.core.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.delivery.restaurants.core.exceptions.CreateRestaurantException;
import dev.thiagooliveira.delivery.restaurants.core.exceptions.RestaurantNotFoundException;
import dev.thiagooliveira.delivery.restaurants.core.exceptions.UpdateRestaurantException;
import dev.thiagooliveira.delivery.restaurants.core.mappers.RestaurantMapper;
import dev.thiagooliveira.delivery.restaurants.core.mappers.RestaurantMapperImpl;
import dev.thiagooliveira.delivery.restaurants.core.model.Address;
import dev.thiagooliveira.delivery.restaurants.core.model.CreateRestaurant;
import dev.thiagooliveira.delivery.restaurants.core.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.core.model.UpdateRestaurant;
import dev.thiagooliveira.delivery.restaurants.core.repositories.RestaurantRepository;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    private static final String RESTAURANT_NAME = "pizza planet";
    private static final String RESTAURANT_DESCRIPTION = "Buzz Lightyear's Pizza Planet Restaurant";
    private static final String RESTAURANT_PHONE = "+351 999 999 999";
    public static final String RESTAURANT_STREET = "Main Street";
    public static final String RESTAURANT_CITY = "Springfield";
    public static final String RESTAURANT_STATE = "IL";
    public static final String RESTAURANT_POSTAL_CODE = "62701";
    public static final String RESTAURANT_COUNTRY = "USA";
    public static final String RESTAURANT_HOUSE_NUMBER = "123";
    public static final String RESTAURANT_APARTMENT_NUMBER = "4B";

    @Mock
    private RestaurantRepository repository;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final RestaurantMapper mapper = new RestaurantMapperImpl();
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        this.restaurantService = new RestaurantServiceImpl(factory.getValidator(), this.mapper, this.repository);
    }

    @Test
    @DisplayName("[error] try to create a restaurant without name")
    void createWithoutName() {
        CreateRestaurantException exception = assertThrows(CreateRestaurantException.class, () -> {
            Restaurant restaurant = restaurantService.create(CreateRestaurant.builder()
                    .description(RESTAURANT_DESCRIPTION)
                    .phoneNumber(RESTAURANT_PHONE)
                    .address(buildAddress())
                    .build());
        });
        assertEquals(CreateRestaurantException.MESSAGE, exception.getMessage());
        assertTrue(exception.getErrors().contains("name: must not be empty"));
    }

    @Test
    @DisplayName("[error] try to create a restaurant without phone number")
    void createWithoutPhoneNumber() {
        CreateRestaurantException exception = assertThrows(CreateRestaurantException.class, () -> {
            Restaurant restaurant = restaurantService.create(CreateRestaurant.builder()
                    .name(RESTAURANT_NAME)
                    .description(RESTAURANT_DESCRIPTION)
                    .address(buildAddress())
                    .build());
        });
        assertEquals(CreateRestaurantException.MESSAGE, exception.getMessage());
        assertTrue(exception.getErrors().contains("phoneNumber: must not be empty"));
    }

    @Test
    @DisplayName("[error] try to update a restaurant without phone number")
    void updateWithoutPhoneNumber() {
        var restaurantId = UUID.randomUUID();
        UpdateRestaurantException exception = assertThrows(UpdateRestaurantException.class, () -> {
            restaurantService.update(
                    restaurantId,
                    UpdateRestaurant.builder()
                            .name(RESTAURANT_NAME)
                            .description(RESTAURANT_DESCRIPTION)
                            .address(buildAddress())
                            .build());
        });
        assertEquals(UpdateRestaurantException.MESSAGE, exception.getMessage());
        assertTrue(exception.getErrors().contains("phoneNumber: must not be empty"));
    }

    @Test
    @DisplayName("[error] restaurant not found when try to update")
    void notFoundWhenTryUpdate() {
        var restaurantId = UUID.randomUUID();
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.update(
                    restaurantId,
                    UpdateRestaurant.builder()
                            .name("new name")
                            .description("new description")
                            .phoneNumber("new phone number")
                            .address(buildAddress())
                            .build());
        });
        assertEquals(RestaurantNotFoundException.format(restaurantId), exception.getMessage());
    }

    @Test
    @DisplayName("[error] try to update a restaurant without name")
    void updateWithoutName() {
        var restaurantId = UUID.randomUUID();
        UpdateRestaurantException exception = assertThrows(UpdateRestaurantException.class, () -> {
            restaurantService.update(
                    restaurantId,
                    UpdateRestaurant.builder()
                            .description(RESTAURANT_DESCRIPTION)
                            .phoneNumber(RESTAURANT_PHONE)
                            .address(buildAddress())
                            .build());
        });
        assertEquals(UpdateRestaurantException.MESSAGE, exception.getMessage());
        assertTrue(exception.getErrors().contains("name: must not be empty"));
    }

    @Test
    @DisplayName("restaurant not found when try to delete")
    void notFoundWhenTryToDelete() {
        var restaurantId = UUID.randomUUID();
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.delete(restaurantId);
        });

        assertEquals(RestaurantNotFoundException.format(restaurantId), exception.getMessage());
    }

    @Test
    @DisplayName("successfully search for a restaurant")
    void get() {
        var restaurantId = UUID.randomUUID();
        when(this.repository.findById(any(UUID.class)))
                .thenReturn(Optional.of(new Restaurant(
                        restaurantId, RESTAURANT_NAME, RESTAURANT_DESCRIPTION, RESTAURANT_PHONE, buildAddress())));
        Optional<Restaurant> optional = restaurantService.get(restaurantId);
        assertTrue(optional.isPresent());
        Restaurant restaurant = optional.get();
        assertEquals(restaurantId, restaurant.getId());
        assertEquals(RESTAURANT_NAME, restaurant.getName());
        assertEquals(RESTAURANT_DESCRIPTION, restaurant.getDescription());
        assertEquals(RESTAURANT_PHONE, restaurant.getPhoneNumber());
    }

    @Test
    @DisplayName("successfully delete a restaurant")
    void delete() {
        var restaurantId = UUID.randomUUID();
        when(this.repository.existsById(any(UUID.class))).thenReturn(true);
        restaurantService.delete(restaurantId);
        verify(this.repository, times(1)).deleteById(restaurantId);
    }

    @Test
    @DisplayName("successfully updates a restaurant")
    void update() {
        var restaurantId = UUID.randomUUID();
        when(this.repository.save(any(Restaurant.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(this.repository.findById(any(UUID.class)))
                .thenReturn(Optional.of(new Restaurant(
                        restaurantId, RESTAURANT_NAME, RESTAURANT_DESCRIPTION, RESTAURANT_PHONE, buildAddress())));
        Restaurant restaurant = restaurantService.update(
                restaurantId,
                UpdateRestaurant.builder()
                        .name("new name")
                        .description("new description")
                        .phoneNumber("new phone number")
                        .address(buildAddress())
                        .build());
        assertEquals(restaurantId, restaurant.getId());
        assertEquals("new name", restaurant.getName());
        assertEquals("new description", restaurant.getDescription());
        assertEquals("new phone number", restaurant.getPhoneNumber());
    }

    @Test
    @DisplayName("create a successful restaurant")
    void create() {
        when(this.repository.save(any(Restaurant.class))).thenAnswer(invocation -> {
            Restaurant restaurant = invocation.getArgument(0, Restaurant.class);
            restaurant.setId(UUID.randomUUID());
            return restaurant;
        });

        Restaurant restaurant = restaurantService.create(CreateRestaurant.builder()
                .name(RESTAURANT_NAME)
                .description(RESTAURANT_DESCRIPTION)
                .phoneNumber(RESTAURANT_PHONE)
                .address(buildAddress())
                .build());
        assertNotNull(restaurant);
        assertNotNull(restaurant.getId());
        assertEquals(RESTAURANT_NAME, restaurant.getName());
        assertEquals(RESTAURANT_DESCRIPTION, restaurant.getDescription());
        assertEquals(RESTAURANT_PHONE, restaurant.getPhoneNumber());
    }

    private static Address buildAddress() {
        return Address.builder()
                .street(RESTAURANT_STREET)
                .city(RESTAURANT_CITY)
                .state(RESTAURANT_STATE)
                .postalCode(RESTAURANT_POSTAL_CODE)
                .country(RESTAURANT_COUNTRY)
                .number(RESTAURANT_HOUSE_NUMBER)
                .build();
    }
}
