package dev.thiagooliveira.delivery.location.services;

import dev.thiagooliveira.delivery.location.clients.DirectionClient;
import dev.thiagooliveira.delivery.location.clients.ValidateAddressClient;
import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final ValidateAddressClient validateAddressClient;
    private final DirectionClient directionClient;

    @Override
    public AddressValidated validate(Address address) {
        return validateAddressClient.validate(address);
    }

    @Override
    public Route route(AddressValidated from, AddressValidated to) {
        return directionClient.direction(from, to);
    }
}
