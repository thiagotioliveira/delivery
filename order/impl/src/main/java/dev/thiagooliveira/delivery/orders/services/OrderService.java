package dev.thiagooliveira.delivery.orders.services;

import dev.thiagooliveira.delivery.orders.dto.*;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    Optional<OrderDetails> getById(UUID id);

    OrderDetails create(CreateOrder createOrder);

    OrderDetails approve(UUID orderId);

    OrderDetails deliver(UUID orderId);

    OrderPage getAll(PageRequest pageRequest);
}
