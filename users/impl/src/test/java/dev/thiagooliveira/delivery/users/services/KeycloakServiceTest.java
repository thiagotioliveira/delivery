package dev.thiagooliveira.delivery.users.services;

import static dev.thiagooliveira.delivery.users.utils.Constants.ATTRIBUTE_ADDRESS_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.delivery.users.dto.UserWithAddressId;
import dev.thiagooliveira.delivery.users.exceptions.UserNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.UserMapperImpl;
import jakarta.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KeycloakServiceTest {

    @Mock
    private RealmResource realm;

    @Mock
    private UsersResource usersResource;

    @Mock
    private UserResource userResource;

    private IAMService keycloakService;

    @BeforeEach
    void setUp() {
        this.keycloakService = new KeycloakService(realm, new UserMapperImpl());
    }

    @Test
    @DisplayName("should return UserWithAddressId when user is found by ID")
    void get_shouldReturnUserWhenFound() {
        UUID userId = UUID.randomUUID();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(userId.toString());

        when(realm.users()).thenReturn(usersResource);
        when(usersResource.get(userId.toString())).thenReturn(userResource);
        when(userResource.toRepresentation()).thenReturn(userRepresentation);

        Optional<UserWithAddressId> result = keycloakService.get(userId);

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("should return empty when user is not found by ID")
    void get_shouldReturnEmptyWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(realm.users()).thenReturn(usersResource);
        when(usersResource.get(userId.toString())).thenReturn(userResource);
        when(userResource.toRepresentation()).thenThrow(new NotFoundException());

        Optional<UserWithAddressId> result = keycloakService.get(userId);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("should return UserWithAddressId when user is found by username")
    void getByUsername_shouldReturnUserWhenFound() {
        String username = "testuser";
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(username);

        when(realm.users()).thenReturn(usersResource);
        when(usersResource.search(username)).thenReturn(List.of(userRepresentation));

        Optional<UserWithAddressId> result = keycloakService.getByUsername(username);

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("should return empty when user is not found by username")
    void getByUsername_shouldReturnEmptyWhenUserNotFound() {
        String username = "testuser";

        when(realm.users()).thenReturn(usersResource);
        when(usersResource.search(username)).thenReturn(List.of());

        Optional<UserWithAddressId> result = keycloakService.getByUsername(username);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("should update the address when user exists")
    void updateCurrentAddress_shouldUpdateAddressWhenUserExists() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(userId.toString());
        userRepresentation.setAttributes(new HashMap<>());

        when(realm.users()).thenReturn(usersResource);
        when(usersResource.get(userId.toString())).thenReturn(userResource);
        when(userResource.toRepresentation()).thenReturn(userRepresentation);

        keycloakService.updateCurrentAddress(userId, addressId);

        verify(userResource).update(any(UserRepresentation.class));
        assertEquals(
                List.of(addressId.toString()),
                userRepresentation.getAttributes().get(ATTRIBUTE_ADDRESS_ID));
    }

    @Test
    @DisplayName("should throw UserNotFoundException when user is not found during address update")
    void updateCurrentAddress_shouldThrowExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        when(realm.users()).thenReturn(usersResource);
        when(usersResource.get(userId.toString())).thenReturn(userResource);
        when(userResource.toRepresentation()).thenThrow(new NotFoundException());

        assertThrows(UserNotFoundException.class, () -> keycloakService.updateCurrentAddress(userId, addressId));
    }
}
