package dev.thiagooliveira.delivery.users.mappers;

import static dev.thiagooliveira.delivery.users.utils.Constants.ATTRIBUTE_ADDRESS_ID;

import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.dto.UserWithAddressId;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {

    @Mapping(target = "currentAddressId", source = "attributes", qualifiedByName = "attributesToCurrentAddressId")
    UserWithAddressId toUser(UserRepresentation userRepresentation);

    @Mapping(target = "id", source = "user.id")
    User toUser(UserWithAddressId user, Address address);

    User toUser(UserWithAddressId user);

    @Named("attributesToCurrentAddressId")
    default UUID attributesToCurrentAddressId(Map<String, List<String>> attributes) {
        if (attributes == null) {
            return null;
        }
        if (attributes.containsKey(ATTRIBUTE_ADDRESS_ID)) {
            return UUID.fromString(attributes.get(ATTRIBUTE_ADDRESS_ID).get(0));
        } else {
            return null;
        }
    }
}
