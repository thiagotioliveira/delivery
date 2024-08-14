package dev.thiagooliveira.delivery.users.services;

import static dev.thiagooliveira.delivery.users.utils.Constants.ATTRIBUTE_ADDRESS_ID;

import dev.thiagooliveira.delivery.users.dto.UserWithAddressId;
import dev.thiagooliveira.delivery.users.exceptions.UserNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import jakarta.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;

@RequiredArgsConstructor
@Slf4j
public class KeycloakService implements IAMService {
    private final RealmResource realm;
    private final UserMapper userMapper;

    @Override
    public Optional<UserWithAddressId> get(UUID userId) {
        return getRepresentation(userId).map(userMapper::toUser);
    }

    @Override
    public Optional<UserWithAddressId> getByUsername(String username) {
        return getRepresentation(username).map(userMapper::toUser);
    }

    @Override
    public void updateCurrentAddress(UUID userId, UUID addressId) {
        UserRepresentation userRepresentation = getRepresentation(userId).orElseThrow(UserNotFoundException::new);
        if (userRepresentation.getAttributes() == null) {
            userRepresentation.setAttributes(new HashMap<>());
        }
        userRepresentation.getAttributes().put(ATTRIBUTE_ADDRESS_ID, List.of(addressId.toString()));
        realm.users().get(userId.toString()).update(userRepresentation);
    }

    private Optional<UserRepresentation> getRepresentation(UUID userId) {
        try {
            return Optional.of(realm.users().get(userId.toString()).toRepresentation());
        } catch (NotFoundException e) {
            log.debug("user not found.", e);
            return Optional.empty();
        }
    }

    private Optional<UserRepresentation> getRepresentation(String username) {
        try {
            return realm.users().search(username).stream().findFirst();
        } catch (NotFoundException e) {
            log.debug("user not found.", e);
            return Optional.empty();
        }
    }
}
