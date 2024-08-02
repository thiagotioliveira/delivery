package dev.thiagooliveira.delivery.location.clients;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;

public interface ValidateAddressClient {
    AddressValidated validate(Address address);
}
