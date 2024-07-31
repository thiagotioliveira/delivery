package dev.thiagooliveira.delivery.restaurants.consumers;

import dev.thiagooliveira.delivery.restaurants.config.AMQPConfig;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUser;
import dev.thiagooliveira.delivery.restaurants.message.dto.CreateRestaurantUserCommand;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateRestaurantUserConsumer {

    private final RestaurantService restaurantService;

    @RabbitListener(queues = AMQPConfig.CREATE_RESTAURANT_USER_QUEUE)
    public void consume(Message<CreateRestaurantUserCommand> message) {
        CreateRestaurantUserCommand command = message.getPayload();
        log.info("Message received {}", command);

        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setUserId(command.getUserId());
        restaurantUser.setRestaurantId(command.getRestaurantId());
        restaurantUser.setDistanceInMeters(2 * new Random().nextDouble()); // TODO
        restaurantService.save(restaurantUser);
    }
}
