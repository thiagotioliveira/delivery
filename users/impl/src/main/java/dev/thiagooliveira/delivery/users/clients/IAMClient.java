package dev.thiagooliveira.delivery.users.clients;

import dev.thiagooliveira.delivery.users.dto.UserWithAddressId;
import java.util.Optional;
import java.util.UUID;

public interface IAMClient {
    Optional<UserWithAddressId> get(UUID userId);

    void updateCurrentAddress(UUID userId, UUID addressId);
    /*
       User updateAddress(UUID id, AddressValidated address);

    */
}
