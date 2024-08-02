package dev.thiagooliveira.delivery.location.utils;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;

public class AddressFormatter {

    public static String format(Address address) {
        return String.format(
                "%s, %s, %s, %s, %s, %s, %s",
                address.getStreet(),
                address.getNumber(),
                address.getNotes(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry());
    }

    public static String format(AddressValidated address) {
        return String.format(
                "%s, %s, %s, %s, %s, %s, %s",
                address.getStreet(),
                address.getNumber(),
                address.getNotes(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry());
    }
}
