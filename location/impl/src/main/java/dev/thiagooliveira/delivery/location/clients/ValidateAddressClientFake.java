package dev.thiagooliveira.delivery.location.clients;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.utils.AddressFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidateAddressClientFake implements ValidateAddressClient {
    @Override
    public AddressValidated validate(Address address) {
        AddressValidated validated = new AddressValidated()
                .street(address.getStreet())
                .number(address.getNumber())
                .notes(address.getNotes())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .state(address.getState())
                .latitude(null)
                .longitude(null)
                .formatted(AddressFormatter.format(address));
        log.debug("address valided. {}", validated);
        return validated;
    }
}
