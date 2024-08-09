package dev.thiagooliveira.delivery.location.services;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;

public interface ValidateAddressService {
    AddressValidated validate(Address address);
}
