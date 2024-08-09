package dev.thiagooliveira.delivery.location.services;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.utils.AddressFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidateAddressServiceImpl implements ValidateAddressService {
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
                .latitude(0d)
                .longitude(0d)
                .formatted(AddressFormatter.format(address));
        log.debug("address valided. {}", validated.getFormatted());
        return validated;
    }
}
