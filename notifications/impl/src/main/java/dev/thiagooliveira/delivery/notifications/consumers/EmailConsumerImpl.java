package dev.thiagooliveira.delivery.notifications.consumers;

import dev.thiagooliveira.delivery.notifications.message.Label;
import dev.thiagooliveira.delivery.notifications.message.dto.EmailCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumerImpl implements EmailConsumer {
    @Override
    @RabbitListener(queues = Label.EMAIL_QUEUE)
    public void consume(Message<EmailCommand> message) {
        log.info("sending a email {}", message.getPayload());
    }
}
