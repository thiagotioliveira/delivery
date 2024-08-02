package dev.thiagooliveira.delivery.restaurants.consumers;

import dev.thiagooliveira.delivery.restaurants.config.AMQPConfig;
import dev.thiagooliveira.delivery.restaurants.mappers.AddressMapper;
import dev.thiagooliveira.delivery.restaurants.message.dto.CreateRestaurantUserCommand;
import dev.thiagooliveira.delivery.restaurants.producers.CreateRestaurantUserProducer;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import dev.thiagooliveira.delivery.users.message.dto.UserAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class FindRestaurantsUserConsumer {

    private final RestaurantService restaurantService;
    private final CreateRestaurantUserProducer createRestaurantUserProducer;
    private final AddressMapper addressMapper;

    @RabbitListener(queues = AMQPConfig.FIND_RESTAURANTS_FOR_USER_QUEUE)
    @Transactional
    public void consume(Message<UserAddress> message) {
        UserAddress userAddress = message.getPayload();
        restaurantService.deleteRestaurantsByUserId(userAddress.getUserId());
        restaurantService
                .getByAddressStateAndAddressCountry(
                        userAddress.getAddress().getState(),
                        userAddress.getAddress().getCountry())
                .stream()
                .parallel()
                .forEach(r -> {
                    var command = new CreateRestaurantUserCommand(
                            r.getRestaurantId(),
                            addressMapper.toAddress(r),
                            userAddress.getUserId(),
                            addressMapper.toAddress(userAddress.getAddress()));
                    createRestaurantUserProducer.send(command);
                });
    }
}
