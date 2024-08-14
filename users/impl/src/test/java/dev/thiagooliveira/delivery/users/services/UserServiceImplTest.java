package dev.thiagooliveira.delivery.users.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.dto.UserWithAddressId;
import dev.thiagooliveira.delivery.users.exceptions.AddressNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.UserMapperImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IAMService iamService;

    @Mock
    private AddressService addressService;

    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userService = new UserServiceImpl(iamService, addressService, new UserMapperImpl());
    }

    @Test
    @DisplayName("should return User when user is found by ID and address is available")
    void getById_userFoundAndAddressAvailable() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UserWithAddressId userWithAddressId = new UserWithAddressId();
        userWithAddressId.setCurrentAddressId(addressId);
        userWithAddressId.setId(userId);
        Address address = new Address().id(addressId);

        when(iamService.get(userId)).thenReturn(Optional.of(userWithAddressId));
        when(addressService.getById(addressId)).thenReturn(Optional.of(address));

        Optional<User> result = userService.getById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals(addressId, result.get().getAddress().getId());
    }

    @Test
    @DisplayName("should return User when user is found by ID and no address is available")
    void getById_userFoundAndNoAddress() {
        UUID userId = UUID.randomUUID();
        UserWithAddressId userWithAddressId = new UserWithAddressId();
        User user = new User();

        when(iamService.get(userId)).thenReturn(Optional.of(userWithAddressId));

        Optional<User> result = userService.getById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    @DisplayName("should return empty when user is not found by ID")
    void getById_userNotFound() {
        UUID userId = UUID.randomUUID();

        when(iamService.get(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getById(userId);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("should throw AddressNotFoundException when address is not found")
    void getById_addressNotFound() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UserWithAddressId userWithAddressId = new UserWithAddressId();
        userWithAddressId.setCurrentAddressId(addressId);

        when(iamService.get(userId)).thenReturn(Optional.of(userWithAddressId));
        when(addressService.getById(addressId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    @DisplayName("should return User when user is found by username and address is available")
    void getByUsername_userFoundAndAddressAvailable() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";
        UUID addressId = UUID.randomUUID();
        UserWithAddressId userWithAddressId = new UserWithAddressId();
        userWithAddressId.setId(userId);
        userWithAddressId.setCurrentAddressId(addressId);
        User user = new User();
        Address address = new Address();

        when(iamService.getByUsername(username)).thenReturn(Optional.of(userWithAddressId));
        when(addressService.getById(addressId)).thenReturn(Optional.of(address));

        Optional<User> result = userService.getByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
    }

    @Test
    @DisplayName("should return User when user is found by username and no address is available")
    void getByUsername_userFoundAndNoAddress() {
        String username = "testuser";
        UserWithAddressId userWithAddressId = new UserWithAddressId();
        User user = new User();

        when(iamService.getByUsername(username)).thenReturn(Optional.of(userWithAddressId));

        Optional<User> result = userService.getByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    @DisplayName("should return empty when user is not found by username")
    void getByUsername_userNotFound() {
        String username = "testuser";

        when(iamService.getByUsername(username)).thenReturn(Optional.empty());

        Optional<User> result = userService.getByUsername(username);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("should throw AddressNotFoundException when address is not found by username")
    void getByUsername_addressNotFound() {
        String username = "testuser";
        UUID addressId = UUID.randomUUID();
        UserWithAddressId userWithAddressId = new UserWithAddressId();
        userWithAddressId.setCurrentAddressId(addressId);

        when(iamService.getByUsername(username)).thenReturn(Optional.of(userWithAddressId));
        when(addressService.getById(addressId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> userService.getByUsername(username));
    }
}
