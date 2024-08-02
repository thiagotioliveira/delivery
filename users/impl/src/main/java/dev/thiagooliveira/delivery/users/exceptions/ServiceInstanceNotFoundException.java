package dev.thiagooliveira.delivery.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceInstanceNotFoundException extends RuntimeException {
    public ServiceInstanceNotFoundException(String serviceId) {
        super(String.format("'%s' not found.", serviceId));
    }
}
