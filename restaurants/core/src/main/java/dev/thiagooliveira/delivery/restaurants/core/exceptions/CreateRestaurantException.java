package dev.thiagooliveira.delivery.restaurants.core.exceptions;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class CreateRestaurantException extends CoreException {
    private final List<String> errors;
    public static final String MESSAGE = "error when trying to create a restaurant.";

    public CreateRestaurantException(List<String> errors) {
        super(MESSAGE);

        this.errors = Collections.unmodifiableList(errors);
    }
}
