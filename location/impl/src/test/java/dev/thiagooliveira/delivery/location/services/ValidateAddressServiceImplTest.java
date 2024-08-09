package dev.thiagooliveira.delivery.location.services;

import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.fixtures.Fixtures;
import dev.thiagooliveira.delivery.location.utils.AddressFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidateAddressServiceImplTest {

    private ValidateAddressService validateAddressService;

    @BeforeEach
    void setUp() {
        this.validateAddressService = new ValidateAddressServiceImpl();
    }

    @Test
    @DisplayName("validate address.")
    void validateTest() {
        Address address = Fixtures.address();
        AddressValidated validated = this.validateAddressService.validate(address);
        assertEquals(address.getStreet(), validated.getStreet());
        assertEquals(address.getNumber(), validated.getNumber());
        assertEquals(address.getNotes(), validated.getNotes());
        assertEquals(address.getNotes(), validated.getNotes());
        assertEquals(address.getCity(), validated.getCity());
        assertEquals(address.getState(), validated.getState());
        assertEquals(address.getPostalCode(), validated.getPostalCode());
        assertEquals(address.getCountry(), validated.getCountry());
        assertEquals(0d, validated.getLatitude());
        assertEquals(0d, validated.getLongitude());
        assertEquals(AddressFormatter.format(address), validated.getFormatted());
    }
}
