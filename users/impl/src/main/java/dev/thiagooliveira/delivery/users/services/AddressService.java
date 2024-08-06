package dev.thiagooliveira.delivery.users.services;

import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.CreateAddress;
import dev.thiagooliveira.delivery.users.dto.UpdateAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressService {
    Optional<Address> getById(UUID addressId);

    Address save(UUID userId, CreateAddress createAddress);

    void delete(UUID userId, UUID addressId);

    List<Address> getByUserId(UUID userId);

    void update(UUID userId, UUID addressId, UpdateAddress updateAddress);
}
