package dev.thiagooliveira.delivery.notifications.consumers;

import dev.thiagooliveira.delivery.orders.dto.OrderDetails;
import org.springframework.messaging.Message;

public interface OrderUpdatedConsumer {

    void consume(Message<OrderDetails> message);
}
