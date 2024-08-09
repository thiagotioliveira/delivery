package dev.thiagooliveira.delivery.orders.services;

import dev.thiagooliveira.delivery.orders.dto.*;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    Optional<OrderDetails> getById(UUID userId, UUID id);

    OrderDetails create(CreateOrder createOrder);

    OrderDetails approve(UUID userId, UUID orderId);

    OrderDetails deliver(UUID userId, UUID orderId);

    OrderPage getAll(UUID userId, PageRequest pageRequest);
}
