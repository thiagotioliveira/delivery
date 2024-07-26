package dev.thiagooliveira.delivery.users.config;

import dev.thiagooliveira.delivery.users.message.Label;
import org.springframework.amqp.core.*;
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
    public TopicExchange userUpdatedTopic() {
        return new TopicExchange(Label.USER_ADDRESS_UPDATED_TOPIC);
    }
}
