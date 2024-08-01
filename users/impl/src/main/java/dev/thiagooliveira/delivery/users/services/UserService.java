package dev.thiagooliveira.delivery.users.services;

import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.User;
import java.util.UUID;

public interface UserService {

    User get(UUID id);

    void updateAddress(UUID id, Address address);
}
