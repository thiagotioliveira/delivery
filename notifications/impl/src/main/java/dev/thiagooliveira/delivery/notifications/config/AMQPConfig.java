package dev.thiagooliveira.delivery.notifications.config;

import dev.thiagooliveira.delivery.orders.message.Label;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

    public static final String NOTIFICATION_ORDER_UPDATED_QUEUE = "notification-order-updated-queue";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue notificationOrderUpdatedQueue() {
        return new Queue(NOTIFICATION_ORDER_UPDATED_QUEUE);
    }

    @Bean
    public TopicExchange orderUpdatedTopic() {
        return new TopicExchange(Label.ORDER_UPDATED_TOPIC);
    }

    @Bean
    public Binding binding(Queue notificationOrderUpdatedQueue, TopicExchange orderUpdatedTopic) {
        return BindingBuilder.bind(notificationOrderUpdatedQueue)
                .to(orderUpdatedTopic)
                .with(Label.ORDER_UPDATED_ROUTING_KEY);
    }
}
