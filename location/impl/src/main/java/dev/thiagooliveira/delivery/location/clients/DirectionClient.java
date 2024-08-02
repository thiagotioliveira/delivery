package dev.thiagooliveira.delivery.location.clients;

import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Route;

public interface DirectionClient {

    Route direction(AddressValidated from, AddressValidated to);
}
