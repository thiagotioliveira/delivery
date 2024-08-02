package dev.thiagooliveira.delivery.restaurants.config;

import dev.thiagooliveira.delivery.users.message.Label;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

    public static final String CREATE_RESTAURANT_USER_QUEUE = "create-restaurant-user-queue";
    public static final String FIND_RESTAURANTS_FOR_USER_QUEUE = "find-restaurants-for-user-queue";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue createRestaurantUserQueue() {
        return new Queue(CREATE_RESTAURANT_USER_QUEUE);
    }

    @Bean
    public Queue findRestaurantsForUserQueue() {
        return new Queue(FIND_RESTAURANTS_FOR_USER_QUEUE);
    }

    @Bean
    public TopicExchange userAddressUpdatedTopic() {
        return new TopicExchange(Label.USER_ADDRESS_UPDATED_TOPIC);
    }

    @Bean
    public Binding binding(Queue findRestaurantsForUserQueue, TopicExchange userAddressUpdatedTopic) {
        return BindingBuilder.bind(findRestaurantsForUserQueue)
                .to(userAddressUpdatedTopic)
                .with(Label.USER_ADDRESS_UPDATED_ROUTING_KEY);
    }
}
