package dev.thiagooliveira.delivery.restaurants.controllers;

import static dev.thiagooliveira.delivery.restaurants.fixtures.Fixtures.restaurantDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.delivery.restaurants.config.security.SecurityConfig;
import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.dto.Restaurant;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantPage;
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

@WebMvcTest(
        controllers = RestaurantAdminController.class,
        excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@Import(SecurityConfig.class)
class RestaurantAdminControllerTest {

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("get restaurant by restaurantId")
    void getRestaurantByIdAsAdmin() throws Exception {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurantDto = restaurantDto(restaurantId);
        when(restaurantService.getById(eq(restaurantId))).thenReturn(Optional.of(restaurantDto));

        MvcResult result = mockMvc.perform(get("/admin/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken()))
                .andExpect(status().isOk())
                .andReturn();
        Restaurant restaurantResult =
                objectMapper.readValue(result.getResponse().getContentAsString(), Restaurant.class);
        assertEquals(restaurantDto, restaurantResult);
    }

    @Test
    @DisplayName("try to get restaurant by restaurantId without privilege.")
    void getRestaurantByIdAsAdminWithoutPrivilege() throws Exception {
        mockMvc.perform(get("/admin/restaurants/{restaurantId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/admin/restaurants/{restaurantId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("get all restaurants.")
    void getRestaurantsAsAdmin() throws Exception {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurantDto = restaurantDto(restaurantId);
        RestaurantPage page = new RestaurantPage().content(List.of(restaurantDto));
        when(restaurantService.getAll(any(PageRequest.class))).thenReturn(page);
        MvcResult result = mockMvc.perform(get("/admin/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createServiceToken()))
                .andExpect(status().isOk())
                .andReturn();
        RestaurantPage pageResult =
                objectMapper.readValue(result.getResponse().getContentAsString(), RestaurantPage.class);
        assertEquals(restaurantDto, pageResult.getContent().getFirst());
    }

    @Test
    @DisplayName("try to get all restaurants without privilege.")
    void getRestaurantsAsAdminWithoutPrivilege() throws Exception {
        mockMvc.perform(get("/admin/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/admin/restaurants").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
