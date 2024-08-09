package dev.thiagooliveira.delivery.location.services.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.maps.addressvalidation.v1.*;
import com.google.type.LatLng;
import dev.thiagooliveira.delivery.location.config.factories.GoogleValidateClientFactory;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.fixtures.Fixtures;
import dev.thiagooliveira.delivery.location.services.ValidateAddressService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GoogleValidateAddressServiceTest {

    @Mock
    private GoogleValidateClientFactory googleValidateClientFactory;

    private ValidateAddressService googleValidateAddressService;

    @BeforeEach
    void setUp() {
        this.googleValidateAddressService = new GoogleValidateAddressService(googleValidateClientFactory);
    }

    @Test
    @DisplayName("validate address.")
    void validateTest() {
        AddressValidationClient addressValidationClient = mock(AddressValidationClient.class);
        ValidateAddressResponse validateAddressResponse = mock(ValidateAddressResponse.class);
        ValidationResult result = mock(ValidationResult.class);
        Verdict verdict = mock(Verdict.class);
        AddressComponent routeStreet = mock(AddressComponent.class);
        AddressComponent routeNumber = mock(AddressComponent.class);
        AddressComponent routeNotes = mock(AddressComponent.class);
        AddressComponent routeCountry = mock(AddressComponent.class);
        AddressComponent routePostalCode = mock(AddressComponent.class);
        AddressComponent routeCity = mock(AddressComponent.class);
        AddressComponent routeState = mock(AddressComponent.class);
        Address address = mock(Address.class);
        Geocode geocode = mock(Geocode.class);
        LatLng latLng = mock(LatLng.class);

        when(address.getFormattedAddress()).thenReturn(Fixtures.ADDRESS_1_FORMATTED_1);
        when(latLng.getLatitude()).thenReturn(Fixtures.ADDRESS_1_LATITUDE);
        when(latLng.getLongitude()).thenReturn(Fixtures.ADDRESS_1_LONGITUDE);
        when(geocode.getLocation()).thenReturn(latLng);
        when(result.getGeocode()).thenReturn(geocode);

        when(routeStreet.getComponentType()).thenReturn("route");
        ComponentName componentName = mock(ComponentName.class);
        when(routeStreet.getComponentName()).thenReturn(componentName);
        when(componentName.getText()).thenReturn(Fixtures.ADDRESS_1_STREET);

        when(routeNumber.getComponentType()).thenReturn("street_number");
        componentName = mock(ComponentName.class);
        when(routeNumber.getComponentName()).thenReturn(componentName);
        when(componentName.getText()).thenReturn(Fixtures.ADDRESS_1_NUMBER);

        when(routeNotes.getComponentType()).thenReturn("subpremise");
        componentName = mock(ComponentName.class);
        when(routeNotes.getComponentName()).thenReturn(componentName);
        when(componentName.getText()).thenReturn(Fixtures.ADDRESS_1_NOTES);

        when(routeCountry.getComponentType()).thenReturn("country");
        componentName = mock(ComponentName.class);
        when(routeCountry.getComponentName()).thenReturn(componentName);
        when(componentName.getText()).thenReturn(Fixtures.ADDRESS_1_COUNTRY);

        when(routePostalCode.getComponentType()).thenReturn("postal_code");
        componentName = mock(ComponentName.class);
        when(routePostalCode.getComponentName()).thenReturn(componentName);
        when(componentName.getText()).thenReturn(Fixtures.ADDRESS_1_POSTAL_CODE);

        when(routeCity.getComponentType()).thenReturn("locality");
        componentName = mock(ComponentName.class);
        when(routeCity.getComponentName()).thenReturn(componentName);
        when(componentName.getText()).thenReturn(Fixtures.ADDRESS_1_CITY);

        when(routeState.getComponentType()).thenReturn("administrative_area_level_2");
        componentName = mock(ComponentName.class);
        when(routeState.getComponentName()).thenReturn(componentName);
        when(componentName.getText()).thenReturn(Fixtures.ADDRESS_1_STATE);

        when(address.getAddressComponentsList())
                .thenReturn(List.of(
                        routeStreet, routeNumber, routeNotes, routeCountry, routePostalCode, routeCity, routeState));
        when(result.getAddress()).thenReturn(address);
        when(verdict.getAddressComplete()).thenReturn(true);
        when(result.getVerdict()).thenReturn(verdict);
        when(validateAddressResponse.getResult()).thenReturn(result);
        when(addressValidationClient.validateAddress(any(ValidateAddressRequest.class)))
                .thenReturn(validateAddressResponse);
        when(googleValidateClientFactory.create()).thenReturn(addressValidationClient);

        dev.thiagooliveira.delivery.location.dto.Address addressInput = Fixtures.address();
        AddressValidated validated = this.googleValidateAddressService.validate(addressInput);
        assertEquals(addressInput.getStreet(), validated.getStreet());
        assertEquals(addressInput.getNumber(), validated.getNumber());
        assertEquals(addressInput.getNotes(), validated.getNotes());
        assertEquals(addressInput.getCountry(), validated.getCountry());
        assertEquals(addressInput.getPostalCode(), validated.getPostalCode());
        assertEquals(addressInput.getCity(), validated.getCity());
        assertEquals(addressInput.getState(), validated.getState());
        assertEquals(Fixtures.ADDRESS_1_LATITUDE, validated.getLatitude());
        assertEquals(Fixtures.ADDRESS_1_LONGITUDE, validated.getLongitude());
    }
}
