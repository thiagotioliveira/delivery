package dev.thiagooliveira.delivery.users.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.delivery.users.config.security.SecurityConfig;
import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.CreateAddress;
import dev.thiagooliveira.delivery.users.dto.UpdateAddress;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.exceptions.AddressException;
import dev.thiagooliveira.delivery.users.exceptions.AddressNotFoundException;
import dev.thiagooliveira.delivery.users.services.AddressService;
import dev.thiagooliveira.delivery.users.services.UserService;
import dev.thiagooliveira.delivery.users.utils.JwtTokenUtil;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@Import(SecurityConfig.class)
class UsersControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private AddressService addressService;

    @MockBean
    private RequestContextManager requestContextManager;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {}

    @Test
    void deleteUserAddress() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        mockMvc.perform(delete("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isNoContent());
        verify(addressService, times(1)).delete(userId, addressId);
    }

    @Test
    void deleteUserAddressAndAddressNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        doThrow(new AddressNotFoundException()).when(addressService).delete(userId, addressId);
        mockMvc.perform(delete("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUserAddressAndDoesNotHaveAddressDefault() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        doThrow(new AddressException("error")).when(addressService).delete(userId, addressId);
        mockMvc.perform(delete("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUserAddressWithoutPrivilege() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        mockMvc.perform(delete("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        when(requestContextManager.getUserAuthenticatedId()).thenReturn(UUID.randomUUID());
        mockMvc.perform(delete("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserAddress() throws Exception {
        UUID userId = UUID.randomUUID();
        Address address = new Address().id(UUID.randomUUID());
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        when(addressService.getByUserId(eq(userId))).thenReturn(List.of(address));
        mockMvc.perform(get("/users/{userId}/addresses", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isOk());
    }

    @Test
    void getUserAddressWithoutPrivilege() throws Exception {
        UUID userId = UUID.randomUUID();
        mockMvc.perform(get("/users/{userId}/addresses", userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        when(requestContextManager.getUserAuthenticatedId()).thenReturn(UUID.randomUUID());
        mockMvc.perform(get("/users/{userId}/addresses", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserById() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User().id(userId);
        when(userService.getById(eq(userId))).thenReturn(Optional.of(user));
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        mockMvc.perform(get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isOk());
    }

    @Test
    void getUserByIdNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userService.getById(eq(userId))).thenReturn(Optional.empty());
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        mockMvc.perform(get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserByIdWithoutPrivilege() throws Exception {
        UUID userId = UUID.randomUUID();
        mockMvc.perform(get("/users/{userId}", userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        when(requestContextManager.getUserAuthenticatedId()).thenReturn(UUID.randomUUID());
        mockMvc.perform(get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchUserAddress() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UpdateAddress updateAddress = new UpdateAddress().current(true);
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        mockMvc.perform(patch("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(updateAddress)))
                .andExpect(status().isNoContent());
        verify(addressService, times(1)).update(eq(userId), eq(addressId), any(UpdateAddress.class));
    }

    @Test
    void patchUserAddressAndAddressNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UpdateAddress updateAddress = new UpdateAddress().current(true);
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        doThrow(new AddressNotFoundException())
                .when(addressService)
                .update(eq(userId), eq(addressId), any(UpdateAddress.class));
        mockMvc.perform(patch("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(updateAddress)))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchUserAddressAndDoesNotHaveAddressDefault() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UpdateAddress updateAddress = new UpdateAddress().current(false);
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        doThrow(new AddressException("error"))
                .when(addressService)
                .update(eq(userId), eq(addressId), any(UpdateAddress.class));
        mockMvc.perform(patch("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(updateAddress)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchUserAddressWithoutPrivilege() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UpdateAddress updateAddress = new UpdateAddress().current(true);
        mockMvc.perform(patch("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAddress)))
                .andExpect(status().isUnauthorized());

        when(requestContextManager.getUserAuthenticatedId()).thenReturn(UUID.randomUUID());
        mockMvc.perform(patch("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(updateAddress)))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchUserAddressWithInvalidPayload() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UpdateAddress updateAddress = new UpdateAddress();
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        mockMvc.perform(patch("/users/{userId}/addresses/{addressId}", userId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(updateAddress)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postUserAddress() throws Exception {
        UUID userId = UUID.randomUUID();

        CreateAddress createAddress = new CreateAddress()
                .description("description")
                .city("city")
                .country("country")
                .notes("notes")
                .number("0")
                .postalCode("9999-999")
                .state("state")
                .street("street");

        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        mockMvc.perform(post("/users/{userId}/addresses", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(createAddress)))
                .andExpect(status().isNoContent());
        verify(addressService, times(1)).save(eq(userId), any(CreateAddress.class));
    }

    @Test
    void postUserAddressAndAddressAlreadyExists() throws Exception {
        UUID userId = UUID.randomUUID();
        CreateAddress createAddress = new CreateAddress()
                .description("description")
                .city("city")
                .country("country")
                .notes("notes")
                .number("0")
                .postalCode("9999-999")
                .state("state")
                .street("street");

        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        when(addressService.save(eq(userId), any(CreateAddress.class))).thenThrow(new AddressException("error"));

        mockMvc.perform(post("/users/{userId}/addresses", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(createAddress)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postUserAddressAndInvalidPayload() throws Exception {
        UUID userId = UUID.randomUUID();
        CreateAddress createAddress = new CreateAddress();

        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        mockMvc.perform(post("/users/{userId}/addresses", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken())
                        .content(objectMapper.writeValueAsString(createAddress)))
                .andExpect(status().isBadRequest());
    }
}
