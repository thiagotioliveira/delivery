package dev.thiagooliveira.delivery.restaurants.controllers;

import static dev.thiagooliveira.delivery.restaurants.fixtures.Fixtures.restaurantUserDetails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.delivery.restaurants.config.security.SecurityConfig;
import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetails;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantUserDetailsPage;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import dev.thiagooliveira.delivery.restaurants.utils.JwtTokenUtil;
import java.util.List;
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

@WebMvcTest(controllers = RestaurantController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@Import(SecurityConfig.class)
class RestaurantControllerTest {

    @MockBean
    private RequestContextManager requestContextManager;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("get restaurant by restaurantId.")
    void getRestaurantById() throws Exception {
        UUID userId = UUID.randomUUID();
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        RestaurantUserDetails restaurantUserDetails = restaurantUserDetails(UUID.randomUUID());
        when(restaurantService.getById(eq(userId), eq(restaurantUserDetails.getId())))
                .thenReturn(Optional.of(restaurantUserDetails));
        MvcResult result = mockMvc.perform(get("/restaurants/{restaurantId}", restaurantUserDetails.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isOk())
                .andReturn();
        RestaurantUserDetails restaurantUserDetailsResult =
                objectMapper.readValue(result.getResponse().getContentAsString(), RestaurantUserDetails.class);
        assertEquals(restaurantUserDetails, restaurantUserDetailsResult);
    }

    @Test
    @DisplayName("try to get restaurant by restaurantId, not found.")
    void getRestaurantByIdNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        UUID restaurantId = UUID.randomUUID();
        when(restaurantService.getById(eq(userId), eq(restaurantId))).thenReturn(Optional.empty());
        mockMvc.perform(get("/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("get all restaurants.")
    void getRestaurants() throws Exception {
        UUID userId = UUID.randomUUID();
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(userId);
        RestaurantUserDetails restaurantUserDetails = restaurantUserDetails(UUID.randomUUID());
        when(restaurantService.getAll(eq(userId), any(PageRequest.class)))
                .thenReturn(new RestaurantUserDetailsPage().content(List.of(restaurantUserDetails)));
        MvcResult result = mockMvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isOk())
                .andReturn();

        RestaurantUserDetailsPage page =
                objectMapper.readValue(result.getResponse().getContentAsString(), RestaurantUserDetailsPage.class);
        assertEquals(1, page.getContent().size());
    }

    @Test
    @DisplayName("try to get by restaurant id without privilege.")
    void getRestaurantByIdWithoutPrivilege() throws Exception {
        mockMvc.perform(get("/restaurants/{restaurantId}", UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("try to get all restaurants without privilege.")
    void getRestaurantsWithoutPrivilege() throws Exception {
        mockMvc.perform(get("/restaurants").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
