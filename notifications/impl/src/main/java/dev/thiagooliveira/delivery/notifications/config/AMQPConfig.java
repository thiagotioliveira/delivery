package dev.thiagooliveira.delivery.notifications.config;

import dev.thiagooliveira.delivery.notifications.message.Label;
import org.springframework.amqp.core.Queue;
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
    public Queue emailQueue() {
        return new Queue(Label.EMAIL_QUEUE);
    }
}
