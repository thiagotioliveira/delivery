package dev.thiagooliveira.delivery.location.services.google;

import com.google.maps.addressvalidation.v1.*;
import com.google.type.PostalAddress;
import dev.thiagooliveira.delivery.location.config.factories.GoogleValidateClientFactory;
import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.exceptions.AddressValidationException;
import dev.thiagooliveira.delivery.location.services.ValidateAddressService;
import dev.thiagooliveira.delivery.location.utils.AddressFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GoogleValidateAddressService implements ValidateAddressService {
    private final GoogleValidateClientFactory googleValidateClientFactory;

    @Override
    public AddressValidated validate(Address address) {
        AddressValidationClient addressValidationClient = googleValidateClientFactory.create();
        ValidateAddressRequest request = ValidateAddressRequest.newBuilder()
                .setAddress(PostalAddress.newBuilder()
                        .addAddressLines(AddressFormatter.format(address))
                        .build())
                .build();
        ValidateAddressResponse response = addressValidationClient.validateAddress(request);
        if (response.getResult().getVerdict().getAddressComplete()) {
            Map<String, AddressComponent> map = response.getResult().getAddress().getAddressComponentsList().stream()
                    .collect(Collectors.toMap(AddressComponent::getComponentType, a -> a));

            String street = getComponentText(map, "route");
            String number = getComponentText(map, "street_number");
            String notes = getComponentText(map, "subpremise");
            String country = getComponentText(map, "country");
            String postalCode = getComponentText(map, "postal_code");
            String city = getComponentText(map, "locality");
            String state = getComponentText(map, "administrative_area_level_2");

            double latitude = response.getResult().getGeocode().getLocation().getLatitude();
            double longitude = response.getResult().getGeocode().getLocation().getLongitude();
            String formattedAddress = response.getResult().getAddress().getFormattedAddress();

            AddressValidated validated = new AddressValidated()
                    .street(street)
                    .number(number)
                    .notes(notes)
                    .country(country)
                    .postalCode(postalCode)
                    .city(city)
                    .state(state)
                    .latitude(latitude)
                    .longitude(longitude)
                    .formatted(formattedAddress);
            log.debug("address valided. {}", validated);
            return validated;
        } else {
            throw new AddressValidationException();
        }
    }

    private static String getComponentText(Map<String, AddressComponent> map, String key) {
        return Optional.ofNullable(map.get(key))
                .map(AddressComponent::getComponentName)
                .map(ComponentName::getText)
                .orElse(null);
    }
}
