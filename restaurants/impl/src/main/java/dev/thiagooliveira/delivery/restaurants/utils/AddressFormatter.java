package dev.thiagooliveira.delivery.restaurants.utils;

import dev.thiagooliveira.delivery.restaurants.model.Address;
import java.util.Objects;

public class AddressFormatter {

    public static String format(Address address) {
        return String.format(
                        "%s, %s, %s, %s, %s, %s, %s",
                        Objects.requireNonNullElse(address.getStreet(), ""),
                        Objects.requireNonNullElse(address.getNumber(), ""),
                        Objects.requireNonNullElse(address.getNotes(), ""),
                        Objects.requireNonNullElse(address.getCity(), ""),
                        Objects.requireNonNullElse(address.getState(), ""),
                        Objects.requireNonNullElse(address.getPostalCode(), ""),
                        Objects.requireNonNullElse(address.getCountry(), ""))
                .replaceAll(", ,", "")
                .trim();
    }

    public static String format(dev.thiagooliveira.delivery.users.message.dto.Address address) {
        return String.format(
                        "%s, %s, %s, %s, %s, %s, %s",
                        Objects.requireNonNullElse(address.getStreet(), ""),
                        Objects.requireNonNullElse(address.getNumber(), ""),
                        Objects.requireNonNullElse(address.getNotes(), ""),
                        Objects.requireNonNullElse(address.getCity(), ""),
                        Objects.requireNonNullElse(address.getState(), ""),
                        Objects.requireNonNullElse(address.getPostalCode(), ""),
                        Objects.requireNonNullElse(address.getCountry(), ""))
                .replaceAll(", ,", "")
                .trim();
    }
}
