package dev.thiagooliveira.delivery.restaurants.producers;

import dev.thiagooliveira.delivery.restaurants.config.AMQPConfig;
import dev.thiagooliveira.delivery.restaurants.message.dto.CreateRestaurantUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRestaurantUserProducerImpl implements CreateRestaurantUserProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(CreateRestaurantUserCommand command) {
        rabbitTemplate.convertAndSend(AMQPConfig.CREATE_RESTAURANT_USER_QUEUE, command);
    }
}
