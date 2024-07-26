package dev.thiagooliveira.delivery.users.clients;

import dev.thiagooliveira.delivery.users.exceptions.UserNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import dev.thiagooliveira.users.spec.dto.Address;
import dev.thiagooliveira.users.spec.dto.User;
import jakarta.ws.rs.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;

@RequiredArgsConstructor
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
            throw new UserNotFoundException(e);
        }
    }
}
