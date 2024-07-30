package dev.thiagooliveira.delivery.orders.validators.handlers;

import dev.thiagooliveira.delivery.orders.dto.CreateOrder;
import dev.thiagooliveira.delivery.orders.mappers.UserMapper;
import dev.thiagooliveira.users.spec.client.UsersApi;
import dev.thiagooliveira.users.spec.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserOrderValidatorHandler implements OrderValidatorHandler {

    private final UsersApi usersApi;
    private final UserMapper userMapper;

    @Override
    public OrderValidatedMap validate(CreateOrder createOrder) {
        User user = usersApi.getUserById(createOrder.getUserId());
        var output = new OrderValidatedMap();
        output.putUser(userMapper.toUser(user));
        return output;
    }
}
