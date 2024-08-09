package dev.thiagooliveira.delivery.restaurants.consumers;

import dev.thiagooliveira.delivery.location.clients.LocationApi;
import dev.thiagooliveira.delivery.location.dto.Directions;
import dev.thiagooliveira.delivery.location.dto.Route;
import dev.thiagooliveira.delivery.restaurants.config.AMQPConfig;
import dev.thiagooliveira.delivery.restaurants.config.factories.LocationApiFactory;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUser;
import dev.thiagooliveira.delivery.restaurants.mappers.AddressMapper;
import dev.thiagooliveira.delivery.restaurants.message.dto.CreateRestaurantUserCommand;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
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
    private final LocationApiFactory locationApiFactory;
    private final AddressMapper addressMapper;

    @RabbitListener(queues = AMQPConfig.CREATE_RESTAURANT_USER_QUEUE)
    public void consume(Message<CreateRestaurantUserCommand> message) {
        CreateRestaurantUserCommand command = message.getPayload();
        log.debug("Message received {}", command);

        LocationApi locationApi = locationApiFactory.create();
        Route route = locationApi.directions(new Directions()
                .origin(addressMapper.toAddressValidated(command.getRestaurantAddress()))
                .destination(addressMapper.toAddressValidated(command.getUserAddress())));

        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setUserId(command.getUserId());
        restaurantUser.setRestaurantId(command.getRestaurantId());
        restaurantUser.setDistance(route.getDistance());
        restaurantUser.setDuration(route.getDuration());
        restaurantService.save(restaurantUser);
    }
}
