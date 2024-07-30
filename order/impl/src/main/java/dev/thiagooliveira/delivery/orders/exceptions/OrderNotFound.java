package dev.thiagooliveira.delivery.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFound extends RuntimeException {
    public OrderNotFound() {
        super("order not found.");
    }
}
