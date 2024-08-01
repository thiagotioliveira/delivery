package dev.thiagooliveira.delivery.users.clients;

import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.User;
import java.util.UUID;

public interface IAMClient {

    User get(UUID id);

    User updateAddress(UUID id, Address address);
}
