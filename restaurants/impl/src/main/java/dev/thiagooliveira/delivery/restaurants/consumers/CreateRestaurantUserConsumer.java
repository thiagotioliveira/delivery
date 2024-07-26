package dev.thiagooliveira.delivery.restaurants.consumers;

import dev.thiagooliveira.delivery.restaurants.config.AMQPConfig;
import dev.thiagooliveira.delivery.restaurants.message.dto.CreateRestaurantUserCommand;
import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantUser;
import dev.thiagooliveira.delivery.restaurants.model.RestaurantUserId;
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
        Random random = new Random(); // TODO

        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setId(new RestaurantUserId(command.getRestaurantId(), command.getUserId()));
        restaurantUser.setRestaurant(new Restaurant());
        restaurantUser.getRestaurant().setId(command.getRestaurantId());
        restaurantUser.setDistanceInMeters(2 * random.nextDouble());
        restaurantService.save(restaurantUser);
    }
}
