package dev.thiagooliveira.delivery.restaurants.core.exceptions;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class UpdateRestaurantException extends CoreException {

    private final List<String> errors;
    public static final String MESSAGE = "error when trying to update the restaurant.";

    public UpdateRestaurantException(List<String> errors) {
        super(MESSAGE);
        this.errors = Collections.unmodifiableList(errors);
    }
}
