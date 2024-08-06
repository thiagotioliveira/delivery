package dev.thiagooliveira.delivery.users.mappers;

import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.CreateAddress;
import dev.thiagooliveira.delivery.users.message.dto.UserAddress;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    Address toAddress(dev.thiagooliveira.delivery.users.model.Address address);

    dev.thiagooliveira.delivery.location.dto.Address toAddress(CreateAddress createAddress);

    dev.thiagooliveira.delivery.users.model.Address toAddress(
            UUID userId, String description, boolean current, AddressValidated addressValidated);

    UserAddress toUserAddress(UUID userId, Address address);
}
