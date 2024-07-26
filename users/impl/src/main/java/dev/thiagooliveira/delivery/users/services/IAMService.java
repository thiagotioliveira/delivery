package dev.thiagooliveira.delivery.users.services;

import dev.thiagooliveira.users.spec.dto.Address;
import dev.thiagooliveira.users.spec.dto.User;
import java.util.UUID;

public interface IAMService {

    User get(UUID id);

    void updateAddress(UUID id, Address address);
}
