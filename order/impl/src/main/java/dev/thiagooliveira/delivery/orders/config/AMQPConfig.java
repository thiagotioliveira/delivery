package dev.thiagooliveira.delivery.orders.config;

import dev.thiagooliveira.delivery.orders.message.Label;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange orderUpdatedTopic() {
        return new TopicExchange(Label.ORDER_UPDATED_TOPIC);
    }
}
