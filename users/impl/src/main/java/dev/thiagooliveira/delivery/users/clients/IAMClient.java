package dev.thiagooliveira.delivery.users.clients;

import dev.thiagooliveira.delivery.users.dto.AddressValidated;
import dev.thiagooliveira.delivery.users.dto.User;
import java.util.UUID;

public interface IAMClient {

    User get(UUID id);

    User updateAddress(UUID id, AddressValidated address);
}
