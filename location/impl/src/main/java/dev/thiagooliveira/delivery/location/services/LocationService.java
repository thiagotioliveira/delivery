package dev.thiagooliveira.delivery.location.services;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Route;

public interface LocationService {

    AddressValidated validate(Address address);

    Route route(AddressValidated from, AddressValidated to);
}
