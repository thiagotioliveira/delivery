package dev.thiagooliveira.delivery.notifications.mappers;

import dev.thiagooliveira.delivery.notifications.dto.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toUser(dev.thiagooliveira.delivery.orders.dto.User user);
}
