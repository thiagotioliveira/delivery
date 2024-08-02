package dev.thiagooliveira.delivery.orders.validators.handlers;

import dev.thiagooliveira.delivery.orders.config.factories.UsersApiFactory;
import dev.thiagooliveira.delivery.orders.dto.CreateOrder;
import dev.thiagooliveira.delivery.orders.mappers.UserMapper;
import dev.thiagooliveira.delivery.users.clients.UsersAdminApi;
import dev.thiagooliveira.delivery.users.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserOrderValidatorHandler implements OrderValidatorHandler {

    private final UsersApiFactory usersApiFactory;
    private final UserMapper userMapper;

    @Override
    public OrderValidatedMap validate(CreateOrder createOrder) {
        UsersAdminApi usersAdminApi = usersApiFactory.create();
        User user = usersAdminApi.getUserByIdAsAdmin(createOrder.getUserId());
        log.debug("user {} validated.", user.getId());
        var output = new OrderValidatedMap();
        output.putUser(userMapper.toUser(user));
        return output;
    }
}
