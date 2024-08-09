package dev.thiagooliveira.delivery.location.services;

import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Route;

public interface DirectionService {

    Route direction(AddressValidated from, AddressValidated to);
}
