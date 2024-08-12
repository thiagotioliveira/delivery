package dev.thiagooliveira.delivery.menus.controllers;

import static dev.thiagooliveira.delivery.menus.fixtures.Fixtures.getMenuDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.delivery.menus.config.security.SecurityConfig;
import dev.thiagooliveira.delivery.menus.dto.MenuItem;
import dev.thiagooliveira.delivery.menus.dto.MenuPage;
import dev.thiagooliveira.delivery.menus.dto.PageRequest;
import dev.thiagooliveira.delivery.menus.services.MenuService;
import dev.thiagooliveira.delivery.menus.utils.JwtTokenUtil;
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

@WebMvcTest(controllers = MenuController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@Import(SecurityConfig.class)
class MenuControllerTest {

    @MockBean
    private MenuService menuService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("get a menu passing restaurant id and menu id.")
    void getItemByRestaurantIdAndItemId() throws Exception {
        MenuItem menuDto = getMenuDto(UUID.randomUUID(), UUID.randomUUID());
        when(menuService.getById(eq(menuDto.getId()))).thenReturn(Optional.of(menuDto));
        MvcResult result = mockMvc.perform(
                        get("/restaurants/{restaurantId}/items/{itemId}", menuDto.getRestaurantId(), menuDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isOk())
                .andReturn();
        MenuItem menuItemDtoResult = objectMapper.readValue(result.getResponse().getContentAsString(), MenuItem.class);
        assertEquals(menuDto.getId(), menuItemDtoResult.getId());
        assertEquals(menuDto.getRestaurantId(), menuItemDtoResult.getRestaurantId());
        assertEquals(menuDto.getDescription(), menuItemDtoResult.getDescription());
        assertEquals(menuDto.getName(), menuItemDtoResult.getName());
        assertEquals(menuDto.getPrice(), menuItemDtoResult.getPrice());
    }

    @Test
    @DisplayName("try to get a menu passing restaurant id and menu id without privilege.")
    void getItemByRestaurantIdAndItemIdWithoutPrivilege() throws Exception {
        mockMvc.perform(get("/restaurants/{restaurantId}/items/{itemId}", UUID.randomUUID(), UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("get a menu passing restaurant id.")
    void getItemsByRestaurantId() throws Exception {
        MenuItem menuDto = getMenuDto(UUID.randomUUID(), UUID.randomUUID());
        when(menuService.getAll(eq(menuDto.getRestaurantId()), any(PageRequest.class)))
                .thenReturn(new MenuPage()
                        .content(List.of(menuDto))
                        .totalElements(1)
                        .totalPages(1)
                        .numberOfElements(1)
                        .first(true)
                        .last(true));
        MvcResult result = mockMvc.perform(get("/restaurants/{restaurantId}/items", menuDto.getRestaurantId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(JwtTokenUtil.createUserToken()))
                .andExpect(status().isOk())
                .andReturn();

        MenuPage menuPage = objectMapper.readValue(result.getResponse().getContentAsString(), MenuPage.class);
        assertEquals(1, menuPage.getNumberOfElements());
        MenuItem menuItem = menuPage.getContent().get(0);
        assertEquals(menuDto.getId(), menuItem.getId());
        assertEquals(menuDto.getRestaurantId(), menuItem.getRestaurantId());
        assertEquals(menuDto.getName(), menuItem.getName());
        assertEquals(menuDto.getDescription(), menuItem.getDescription());
        assertEquals(menuDto.getPrice(), menuItem.getPrice());
    }

    @Test
    @DisplayName("try to get a menu passing restaurant id without privilege.")
    void getItemsByRestaurantIdWithoutPrivilege() throws Exception {
        mockMvc.perform(get("/restaurants/{restaurantId}/items", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
