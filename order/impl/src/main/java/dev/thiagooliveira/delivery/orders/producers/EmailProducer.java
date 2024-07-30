package dev.thiagooliveira.delivery.orders.producers;

import dev.thiagooliveira.delivery.notifications.message.dto.EmailCommand;

public interface EmailProducer {

    void send(EmailCommand command);
}
