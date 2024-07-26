package dev.thiagooliveira.delivery.users.mappers;

import dev.thiagooliveira.delivery.users.message.dto.UserAddress;
import dev.thiagooliveira.users.spec.dto.Address;
import dev.thiagooliveira.users.spec.dto.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {
    @Mapping(target = "attributes", source = "address", qualifiedByName = "addressToAttributes")
    UserRepresentation toUserRepresentation(User user);

    @Mapping(target = "address", source = "attributes", qualifiedByName = "attributesToAddress")
    User toUser(UserRepresentation userRepresentation);

    @Mapping(target = "userId", source = "id")
    UserAddress toUserAddress(User user);

    @Named("attributesToAddress")
    default Address mapAttributesToAddress(Map<String, List<String>> attributes) {
        return attributesToAddress(attributes);
    }

    @Named("addressToAttributes")
    default Map<String, List<String>> mapAddressToAttributes(Address address) {
        return addressToAttributes(address);
    }

    private static Address attributesToAddress(Map<String, List<String>> attributes) {
        if (attributes == null) {
            return null;
        }
        Address address = new Address();
        address.setStreet(getFirstElement(attributes.get("street")));
        address.setCity(getFirstElement(attributes.get("city")));
        address.setNotes(getFirstElement(attributes.get("notes")));
        address.setNumber(getFirstElement(attributes.get("number")));
        address.setState(getFirstElement(attributes.get("state")));
        address.setPostalCode(getFirstElement(attributes.get("postalCode")));
        address.setCountry(getFirstElement(attributes.get("country")));

        return address;
    }

    private static Map<String, List<String>> addressToAttributes(Address address) {
        if (address == null) {
            return null;
        }

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("street", Collections.singletonList(address.getStreet()));
        attributes.put("city", Collections.singletonList(address.getCity()));
        attributes.put("notes", Collections.singletonList(address.getCity()));
        attributes.put("state", Collections.singletonList(address.getState()));
        attributes.put("postalCode", Collections.singletonList(address.getPostalCode()));
        attributes.put("country", Collections.singletonList(address.getCountry()));

        return attributes;
    }

    private static String getFirstElement(List<String> list) {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
