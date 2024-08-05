package dev.thiagooliveira.delivery.orders.producers;

import dev.thiagooliveira.delivery.orders.dto.OrderDetails;

public interface OrderUpdatedProducer {

    void send(OrderDetails orderDetails);
}
