package dev.thiagooliveira.delivery.orders.producers;

import dev.thiagooliveira.delivery.notifications.message.Label;
import dev.thiagooliveira.delivery.notifications.message.dto.EmailCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProducerImpl implements EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(EmailCommand command) {
        rabbitTemplate.convertAndSend(Label.EMAIL_QUEUE, command);
    }
}
