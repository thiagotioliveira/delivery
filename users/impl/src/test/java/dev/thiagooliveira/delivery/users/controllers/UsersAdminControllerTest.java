package dev.thiagooliveira.delivery.users.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.delivery.users.config.security.SecurityConfig;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.services.UserService;
import dev.thiagooliveira.delivery.users.utils.JwtTokenUtil;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(controllers = UsersAdminController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@Import(SecurityConfig.class)
class UsersAdminControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("get user by ID as Admin - Success")
    void getUserByIdAsAdmin() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User().id(userId);
        when(userService.getById(eq(userId))).thenReturn(Optional.of(user));

        MvcResult result = mockMvc.perform(get("/admin/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken()))
                .andExpect(status().isOk())
                .andReturn();

        User userResult = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals(userId, userResult.getId());
    }

    @Test
    @DisplayName("get user by ID as Admin - User Not Found")
    void getUserByIdAsAdminUserNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userService.getById(eq(userId))).thenReturn(Optional.empty());

        mockMvc.perform(get("/admin/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("get user by ID as Admin - User Without Privilege")
    void getUserByIdAsAdminUserWithoutPrivilege() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userService.getById(eq(userId))).thenReturn(Optional.empty());

        mockMvc.perform(get("/admin/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/admin/users/{userId}", userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
