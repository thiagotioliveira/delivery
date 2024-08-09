package dev.thiagooliveira.delivery.location.utils;

import dev.thiagooliveira.delivery.location.dto.Address;
import java.util.Objects;

public class AddressFormatter {

    private static final String EMPTY = "";

    public static String format(Address address) {
        return String.format(
                        "%s, %s, %s, %s, %s, %s, %s",
                        Objects.requireNonNullElse(address.getStreet(), EMPTY),
                        Objects.requireNonNullElse(address.getNumber(), EMPTY),
                        Objects.requireNonNullElse(address.getNotes(), EMPTY),
                        Objects.requireNonNullElse(address.getCity(), EMPTY),
                        Objects.requireNonNullElse(address.getState(), EMPTY),
                        Objects.requireNonNullElse(address.getPostalCode(), EMPTY),
                        Objects.requireNonNullElse(address.getCountry(), EMPTY))
                .replaceAll(", ,", EMPTY)
                .trim();
    }
}
