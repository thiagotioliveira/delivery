package dev.thiagooliveira.delivery.restaurants.core.exceptions;

import java.util.UUID;

public class RestaurantNotFoundException extends CoreException {

    public static final String MESSAGE = "restaurant #%s not found.";

    public RestaurantNotFoundException(UUID ui) {
        super(format(ui));
    }

    public static String format(UUID ui) {
        return String.format(MESSAGE, ui);
    }
}
