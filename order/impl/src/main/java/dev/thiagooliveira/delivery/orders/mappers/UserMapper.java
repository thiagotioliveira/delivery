package dev.thiagooliveira.delivery.orders.mappers;

import dev.thiagooliveira.delivery.orders.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "name", expression = "java(concatenateNames(user.getFirstName(), user.getLastName()))")
    User toUser(dev.thiagooliveira.delivery.users.dto.User user);

    default String concatenateNames(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}
