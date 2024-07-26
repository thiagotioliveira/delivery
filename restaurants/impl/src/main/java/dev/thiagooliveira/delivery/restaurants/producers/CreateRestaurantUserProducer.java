package dev.thiagooliveira.delivery.restaurants.producers;

import dev.thiagooliveira.delivery.restaurants.message.dto.CreateRestaurantUserCommand;

public interface CreateRestaurantUserProducer {

    public void send(CreateRestaurantUserCommand command);
}
