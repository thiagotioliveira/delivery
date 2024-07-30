package dev.thiagooliveira.delivery.orders.validators.handlers;

import dev.thiagooliveira.delivery.orders.spec.dto.CreateOrder;

public interface OrderValidatorHandler {

    OrderValidatedMap validate(CreateOrder createOrder);
}
