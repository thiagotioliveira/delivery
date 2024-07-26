package dev.thiagooliveira.delivery.users.clients;

import dev.thiagooliveira.users.spec.dto.Address;
import dev.thiagooliveira.users.spec.dto.User;
import java.util.UUID;

public interface IAMClient {

    User get(UUID id);

    User updateAddress(UUID id, Address address);
}
