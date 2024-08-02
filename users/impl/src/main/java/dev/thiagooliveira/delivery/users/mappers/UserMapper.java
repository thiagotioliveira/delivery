package dev.thiagooliveira.delivery.users.mappers;

import dev.thiagooliveira.delivery.users.dto.AddressValidated;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.message.dto.UserAddress;
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
    default AddressValidated mapAttributesToAddress(Map<String, List<String>> attributes) {
        return attributesToAddress(attributes);
    }

    @Named("addressToAttributes")
    default Map<String, List<String>> mapAddressToAttributes(AddressValidated address) {
        return addressToAttributes(address);
    }

    private static AddressValidated attributesToAddress(Map<String, List<String>> attributes) {
        if (attributes == null) {
            return null;
        }
        AddressValidated address = new AddressValidated();
        address.setStreet(getFirstElement(attributes.get("street")));
        address.setCity(getFirstElement(attributes.get("city")));
        address.setNotes(getFirstElement(attributes.get("notes")));
        address.setNumber(getFirstElement(attributes.get("number")));
        address.setState(getFirstElement(attributes.get("state")));
        address.setPostalCode(getFirstElement(attributes.get("postalCode")));
        address.setCountry(getFirstElement(attributes.get("country")));
        address.setFormatted(getFirstElement(attributes.get("formatted")));
        String latitude = getFirstElement(attributes.get("latitude"));
        if (latitude != null) {
            address.setLatitude(Double.valueOf(latitude));
        }
        String longitude = getFirstElement(attributes.get("longitude"));
        if (longitude != null) {
            address.setLongitude(Double.valueOf(longitude));
        }
        return address;
    }

    private static Map<String, List<String>> addressToAttributes(AddressValidated address) {
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
        attributes.put("number", Collections.singletonList(address.getNumber()));
        attributes.put("formatted", Collections.singletonList(address.getFormatted()));
        if (address.getLatitude() != null) {
            attributes.put(
                    "latitude", Collections.singletonList(address.getLatitude().toString()));
        }
        if (address.getLongitude() != null) {
            attributes.put(
                    "longitude",
                    Collections.singletonList(address.getLongitude().toString()));
        }

        return attributes;
    }

    private static String getFirstElement(List<String> list) {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
