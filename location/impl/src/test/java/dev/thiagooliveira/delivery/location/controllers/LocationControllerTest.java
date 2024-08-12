package dev.thiagooliveira.delivery.location.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.delivery.location.config.security.SecurityConfig;
import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Directions;
import dev.thiagooliveira.delivery.location.dto.Route;
import dev.thiagooliveira.delivery.location.fixtures.Fixtures;
import dev.thiagooliveira.delivery.location.services.DirectionService;
import dev.thiagooliveira.delivery.location.services.ValidateAddressService;
import dev.thiagooliveira.delivery.location.utils.JwtTokenUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = LocationController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@Import(SecurityConfig.class)
class LocationControllerTest {

    @MockBean
    private DirectionService directionService;

    @MockBean
    private ValidateAddressService validateAddressService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("receives forbidden when trying to get directions without privilege.")
    void directionsWithoutPrivilege() throws Exception {
        mockMvc.perform(post("/admin/directions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Fixtures.directions())))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/admin/directions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(Fixtures.directions())))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("receives forbidden when trying to validate address without privilege.")
    void validateAddressWithoutPrivilege() throws Exception {
        mockMvc.perform(post("/admin/address/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Fixtures.address())))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/admin/address/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(Fixtures.address())))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("calculates the route between two addresses.")
    void directionsTest() throws Exception {
        Route route = Fixtures.route();
        when(directionService.direction(any(AddressValidated.class), any(AddressValidated.class)))
                .thenReturn(route);
        MvcResult result = mockMvc.perform(post("/admin/directions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken())
                        .content(objectMapper.writeValueAsString(Fixtures.directions())))
                .andExpect(status().isOk())
                .andReturn();
        Route routeResult = objectMapper.readValue(result.getResponse().getContentAsString(), Route.class);
        assertEquals(route.getDistance(), routeResult.getDistance());
        assertEquals(route.getDuration(), routeResult.getDuration());
    }

    @Test
    @DisplayName("receives bad request when trying to get directions with missing fields.")
    void directionsMissingFields() throws Exception {
        mockMvc.perform(post("/admin/directions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken())
                        .content(objectMapper.writeValueAsString(new Directions())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.origin").value("must not be null"))
                .andExpect(jsonPath("$.destination").value("must not be null"));

        mockMvc.perform(post("/admin/directions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken())
                        .content(objectMapper.writeValueAsString(
                                new Directions().origin(new AddressValidated()).destination(new AddressValidated()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['origin.street']").value("must not be null"))
                .andExpect(jsonPath("$.['origin.number']").value("must not be null"))
                .andExpect(jsonPath("$.['origin.city']").value("must not be null"))
                .andExpect(jsonPath("$.['origin.state']").value("must not be null"))
                .andExpect(jsonPath("$.['origin.postalCode']").value("must not be null"))
                .andExpect(jsonPath("$.['origin.country']").value("must not be null"))
                .andExpect(jsonPath("$.['origin.formatted']").value("must not be null"))
                .andExpect(jsonPath("$.['origin.longitude']").value("must not be null"))
                .andExpect(jsonPath("$.['origin.latitude']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.street']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.number']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.city']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.state']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.postalCode']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.country']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.formatted']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.longitude']").value("must not be null"))
                .andExpect(jsonPath("$.['destination.latitude']").value("must not be null"));
    }

    @Test
    @DisplayName("validate address.")
    void validateAddress() throws Exception {
        AddressValidated addressValidated = Fixtures.from();
        when(validateAddressService.validate(any(Address.class))).thenReturn(addressValidated);

        MvcResult result = mockMvc.perform(post("/admin/address/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken())
                        .content(objectMapper.writeValueAsString(Fixtures.address())))
                .andExpect(status().isOk())
                .andReturn();
        AddressValidated addressValidatedResult =
                objectMapper.readValue(result.getResponse().getContentAsString(), AddressValidated.class);
        assertEquals(addressValidated.getStreet(), addressValidatedResult.getStreet());
        assertEquals(addressValidated.getNumber(), addressValidatedResult.getNumber());
        assertEquals(addressValidated.getNotes(), addressValidatedResult.getNotes());
        assertEquals(addressValidated.getCity(), addressValidatedResult.getCity());
        assertEquals(addressValidated.getState(), addressValidatedResult.getState());
        assertEquals(addressValidated.getPostalCode(), addressValidatedResult.getPostalCode());
        assertEquals(addressValidated.getCountry(), addressValidatedResult.getCountry());
        assertEquals(addressValidated.getFormatted(), addressValidatedResult.getFormatted());
        assertEquals(addressValidated.getLatitude(), addressValidatedResult.getLatitude());
    }

    @Test
    @DisplayName("receives bad request when trying to validate address with missing fields.")
    void validateAddressMissingFields() throws Exception {
        mockMvc.perform(post("/admin/address/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken())
                        .content(objectMapper.writeValueAsString(new Address())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.street").value("must not be null"))
                .andExpect(jsonPath("$.number").value("must not be null"))
                .andExpect(jsonPath("$.city").value("must not be null"))
                .andExpect(jsonPath("$.state").value("must not be null"))
                .andExpect(jsonPath("$.postalCode").value("must not be null"))
                .andExpect(jsonPath("$.country").value("must not be null"));
    }
}
