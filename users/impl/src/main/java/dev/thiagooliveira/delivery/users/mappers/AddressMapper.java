package dev.thiagooliveira.delivery.users.mappers;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.users.dto.AddressValidated;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    Address toAddress(dev.thiagooliveira.delivery.users.dto.Address address);

    AddressValidated toAddressValidated(dev.thiagooliveira.delivery.location.dto.AddressValidated addressValidated);
}
