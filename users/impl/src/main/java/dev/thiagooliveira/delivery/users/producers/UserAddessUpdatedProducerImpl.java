package dev.thiagooliveira.delivery.users.producers;

import dev.thiagooliveira.delivery.users.message.Label;
import dev.thiagooliveira.delivery.users.message.dto.UserAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserAddessUpdatedProducerImpl implements UserAddessUpdatedProducer {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(UserAddress user) {
        log.debug("sending {}", user);
        this.rabbitTemplate.convertAndSend(
                Label.USER_ADDRESS_UPDATED_TOPIC,
                String.format("%s.updated", Label.USER_ADDRESS_UPDATED_ROUTING_KEY),
                user);
    }
}
