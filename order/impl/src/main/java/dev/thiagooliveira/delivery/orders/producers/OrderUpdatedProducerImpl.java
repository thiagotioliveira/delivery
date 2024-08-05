package dev.thiagooliveira.delivery.orders.producers;

import dev.thiagooliveira.delivery.orders.dto.OrderDetails;
import dev.thiagooliveira.delivery.orders.message.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderUpdatedProducerImpl implements OrderUpdatedProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(OrderDetails orderDetails) {
        rabbitTemplate.convertAndSend(Label.ORDER_UPDATED_TOPIC, Label.ORDER_UPDATED_ROUTING_KEY, orderDetails);
    }
}
