package dev.thiagooliveira.delivery.users.clients;

import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.exceptions.UserNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import jakarta.ws.rs.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;

@RequiredArgsConstructor
@Slf4j
public class KeycloakClient implements IAMClient {
    private final RealmResource realm;
    private final UserMapper userMapper;

    @Override
    public User get(UUID id) {
        return userMapper.toUser(getRepresentation(id));
    }

    @Override
    public User updateAddress(UUID id, Address address) {
        User user = get(id);
        user.setAddress(address);
        UserRepresentation userRepresentation = userMapper.toUserRepresentation(user);
        realm.users().get(id.toString()).update(userRepresentation);
        return user;
    }

    private UserRepresentation getRepresentation(UUID id) {
        try {
            return realm.users().get(id.toString()).toRepresentation();
        } catch (NotFoundException e) {
            log.debug("user not found.", e);
            throw new UserNotFoundException();
        }
    }
}
