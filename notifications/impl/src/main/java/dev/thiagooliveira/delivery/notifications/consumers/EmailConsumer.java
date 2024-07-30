package dev.thiagooliveira.delivery.notifications.consumers;

import dev.thiagooliveira.delivery.notifications.message.dto.EmailCommand;
import org.springframework.messaging.Message;

public interface EmailConsumer {

    void consume(Message<EmailCommand> message);
}
