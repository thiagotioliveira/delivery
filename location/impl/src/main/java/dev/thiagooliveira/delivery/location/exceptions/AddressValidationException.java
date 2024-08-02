package dev.thiagooliveira.delivery.location.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AddressValidationException extends RuntimeException {

    public AddressValidationException() {
        super("invalid address.");
    }
}
