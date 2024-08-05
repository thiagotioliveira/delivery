package dev.thiagooliveira.delivery.notifications.consumers;

import dev.thiagooliveira.delivery.notifications.config.AMQPConfig;
import dev.thiagooliveira.delivery.notifications.dto.Notification;
import dev.thiagooliveira.delivery.notifications.mappers.UserMapper;
import dev.thiagooliveira.delivery.notifications.service.NotificationService;
import dev.thiagooliveira.delivery.orders.dto.OrderDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderUpdatedConsumerImpl implements OrderUpdatedConsumer {
    private final NotificationService notificationService;
    private final UserMapper userMapper;

    @Override
    @RabbitListener(queues = AMQPConfig.NOTIFICATION_ORDER_UPDATED_QUEUE)
    public void consume(Message<OrderDetails> message) {
        OrderDetails payload = message.getPayload();
        notificationService.send(Notification.builder()
                .user(userMapper.toUser(payload.getUser()))
                .message(String.format("order '%s' was updated. status: '%s'", payload.getId(), payload.getStatus()))
                .build());
    }
}
