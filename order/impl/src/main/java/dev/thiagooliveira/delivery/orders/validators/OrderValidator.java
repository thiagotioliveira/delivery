package dev.thiagooliveira.delivery.orders.validators;

import dev.thiagooliveira.delivery.orders.dto.CreateOrder;
import dev.thiagooliveira.delivery.orders.validators.handlers.OrderValidatorResult;

public interface OrderValidator {

    OrderValidatorResult validate(CreateOrder createOrder);
}
