package dev.thiagooliveira.delivery.restaurants.consumers;

import dev.thiagooliveira.delivery.restaurants.config.AMQPConfig;
import dev.thiagooliveira.delivery.restaurants.message.dto.CreateRestaurantUserCommand;
import dev.thiagooliveira.delivery.restaurants.model.Address;
import dev.thiagooliveira.delivery.restaurants.producers.CreateRestaurantUserProducer;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import dev.thiagooliveira.delivery.users.message.dto.UserAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteRestaurantsToUserConsumer {

    private final RestaurantService restaurantService;
    private final CreateRestaurantUserProducer createRestaurantUserProducer;

    @RabbitListener(queues = AMQPConfig.DELETE_RESTAURANTS_TO_USER_QUEUE)
    public void consume(Message<UserAddress> message) {
        UserAddress userAddress = message.getPayload();
        restaurantService.deleteRestaurantsByUserId(userAddress.getUserId());
        restaurantService
                .findByAddressStateAndAddressCountry(
                        userAddress.getAddress().getState(),
                        userAddress.getAddress().getCountry())
                .stream()
                .parallel()
                .forEach(r -> {
                    var command = new CreateRestaurantUserCommand(
                            r.getRestaurantId(),
                            Address.builder()
                                    .street(r.getStreet())
                                    .number(r.getNumber())
                                    .notes(r.getNotes())
                                    .state(r.getState())
                                    .city(r.getCity())
                                    .country(r.getCountry())
                                    .postalCode(r.getPostalCode())
                                    .build(),
                            userAddress.getUserId(),
                            Address.builder()
                                    .street(userAddress.getAddress().getStreet())
                                    .number(userAddress.getAddress().getNumber())
                                    .notes(userAddress.getAddress().getNotes())
                                    .state(userAddress.getAddress().getState())
                                    .city(userAddress.getAddress().getCity())
                                    .country(userAddress.getAddress().getCountry())
                                    .postalCode(userAddress.getAddress().getPostalCode())
                                    .build());
                    createRestaurantUserProducer.send(command);
                });
    }
}
