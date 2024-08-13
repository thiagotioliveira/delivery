package dev.thiagooliveira.delivery.restaurants.service;

import static dev.thiagooliveira.delivery.restaurants.fixtures.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetails;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetailsPage;
import dev.thiagooliveira.delivery.restaurants.mappers.RestaurantMapperImpl;
import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantUser;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantRepository;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantUserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantUserRepository restaurantUserRepository;

    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        this.restaurantService =
                new RestaurantServiceImpl(new RestaurantMapperImpl(), restaurantRepository, restaurantUserRepository);
    }

    @Test
    @DisplayName("get all restaurants for user.")
    void getAll() {
        var restaurant = restaurantUser(UUID.randomUUID(), UUID.randomUUID());
        var content = List.of(restaurant);
        var page = new PageImpl<RestaurantUser>(content, PageRequest.of(0, 1), content.size());
        when(restaurantUserRepository.findByIdUserId(eq(restaurant.getId().getUserId()), any(PageRequest.class)))
                .thenReturn(page);
        RestaurantUserDetailsPage pageResult = this.restaurantService.getAll(
                restaurant.getId().getUserId(),
                new dev.thiagooliveira.delivery.restaurants.dto.PageRequest()
                        .pageNumber(0)
                        .pageSize(10));

        assertTrue(pageResult.getFirst());
        assertTrue(pageResult.getLast());
        assertEquals(0, pageResult.getNumber());
        assertEquals(1, pageResult.getTotalPages());
        assertEquals(1, pageResult.getNumberOfElements());
        assertEquals(1, pageResult.getContent().size());
        RestaurantUserDetails restaurantUserDetails = pageResult.getContent().get(0);
        assertEquals(restaurant.getRestaurant().getId(), restaurantUserDetails.getId());
        assertEquals(restaurant.getRestaurant().getDescription(), restaurantUserDetails.getDescription());
        assertEquals(restaurant.getRestaurant().getPhoneNumber(), restaurantUserDetails.getPhoneNumber());
        assertEquals(
                restaurant.getRestaurant().getAddress().getStreet(),
                restaurantUserDetails.getAddress().getStreet());
        assertEquals(
                restaurant.getRestaurant().getAddress().getNumber(),
                restaurantUserDetails.getAddress().getNumber());
        assertEquals(
                restaurant.getRestaurant().getAddress().getCity(),
                restaurantUserDetails.getAddress().getCity());
        assertEquals(
                restaurant.getRestaurant().getAddress().getState(),
                restaurantUserDetails.getAddress().getState());
        assertEquals(
                restaurant.getRestaurant().getAddress().getPostalCode(),
                restaurantUserDetails.getAddress().getPostalCode());
        assertEquals(
                restaurant.getRestaurant().getAddress().getCountry(),
                restaurantUserDetails.getAddress().getCountry());
        assertEquals(
                restaurant.getRestaurant().getAddress().getNotes(),
                restaurantUserDetails.getAddress().getNotes());
    }

    @Test
    @DisplayName("get all restaurants - admin")
    void testGetAll() {
        var restaurant1 = restaurant(UUID.randomUUID());
        var restaurant2 = restaurant(UUID.randomUUID());
        var content = List.of(restaurant1, restaurant2);
        var page = new PageImpl<Restaurant>(content, PageRequest.of(0, 2), content.size());
        when(restaurantRepository.findAll(any(PageRequest.class))).thenReturn(page);

        var pageResult = this.restaurantService.getAll(new dev.thiagooliveira.delivery.restaurants.dto.PageRequest()
                .pageNumber(0)
                .pageSize(10));

        assertTrue(pageResult.getFirst());
        assertTrue(pageResult.getLast());
        assertEquals(0, pageResult.getNumber());
        assertEquals(1, pageResult.getTotalPages());
        assertEquals(2, pageResult.getNumberOfElements());
        assertEquals(2, pageResult.getContent().size());
        assertEquals(restaurant1.getId(), pageResult.getContent().get(0).getId());
        assertEquals(restaurant2.getId(), pageResult.getContent().get(1).getId());
    }

    @Test
    @DisplayName("get restaurant by userId and restaurantId")
    void getById() {
        var restaurantUser = restaurantUser(UUID.randomUUID(), UUID.randomUUID());
        when(restaurantUserRepository.findByIdRestaurantIdAndIdUserId(
                        eq(restaurantUser.getId().getRestaurantId()),
                        eq(restaurantUser.getId().getUserId())))
                .thenReturn(Optional.of(restaurantUser));

        var result = this.restaurantService.getById(
                restaurantUser.getId().getUserId(), restaurantUser.getId().getRestaurantId());

        assertTrue(result.isPresent());
        assertEquals(restaurantUser.getRestaurant().getId(), result.get().getId());
    }

    @Test
    @DisplayName("get restaurant by restaurantId - admin")
    void testGetById() {
        var restaurant = restaurant(UUID.randomUUID());
        when(restaurantRepository.findById(eq(restaurant.getId()))).thenReturn(Optional.of(restaurant));

        var result = this.restaurantService.getById(restaurant.getId());

        assertTrue(result.isPresent());
        assertEquals(restaurant.getId(), result.get().getId());
    }

    @Test
    @DisplayName("delete restaurants by userId")
    void deleteRestaurantsByUserId() {
        var userId = UUID.randomUUID();
        this.restaurantService.deleteRestaurantsByUserId(userId);
        Mockito.verify(restaurantUserRepository).deleteByIdUserId(userId);
    }

    @Test
    @DisplayName("save restaurant")
    void save() {
        UUID restaurantId = UUID.randomUUID();
        var restaurantDto = restaurantDto(restaurantId);
        var restaurant = restaurant(restaurantId);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        var result = this.restaurantService.save(restaurantDto);

        assertEquals(restaurant.getId(), result.getId());
        assertEquals(restaurant.getName(), result.getName());
    }

    @Test
    @DisplayName("save restaurant user")
    void testSave() {
        UUID userId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        var restaurantUserDto = restaurantUserDto(userId, restaurantId);
        var restaurantUser = restaurantUser(userId, restaurantId);
        when(restaurantUserRepository.save(any(RestaurantUser.class))).thenReturn(restaurantUser);

        var result = this.restaurantService.save(restaurantUserDto);

        assertEquals(restaurantUser.getDuration().toString(), result.getDuration());
        assertEquals(restaurantUser.getDistance().toString(), result.getDistance());
        assertEquals(restaurantUser.getId().getRestaurantId(), result.getRestaurantId());
        assertEquals(restaurantUser.getId().getUserId(), result.getUserId());
    }

    @Test
    @DisplayName("get restaurants by state and country")
    void getByAddressStateAndAddressCountry() {
        var restaurant1 = restaurantIdWithAddress(UUID.randomUUID());
        var restaurant2 = restaurantIdWithAddress(UUID.randomUUID());
        when(restaurantRepository.findByAddressStateAndAddressCountry(eq("Lisbon"), eq("Portugal")))
                .thenReturn(List.of(restaurant1, restaurant2));

        var result = this.restaurantService.getByAddressStateAndAddressCountry("Lisbon", "Portugal");

        assertEquals(2, result.size());
        assertEquals(restaurant1.getRestaurantId(), result.get(0).getRestaurantId());
        assertEquals(restaurant2.getRestaurantId(), result.get(1).getRestaurantId());
    }
}
