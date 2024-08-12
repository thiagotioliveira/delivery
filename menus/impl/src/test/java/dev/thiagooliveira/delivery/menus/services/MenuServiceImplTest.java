package dev.thiagooliveira.delivery.menus.services;

import static dev.thiagooliveira.delivery.menus.fixtures.Fixtures.getMenu;
import static dev.thiagooliveira.delivery.menus.fixtures.Fixtures.getMenuDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.delivery.menus.config.factories.RestaurantAdminApiFactory;
import dev.thiagooliveira.delivery.menus.dto.MenuItem;
import dev.thiagooliveira.delivery.menus.dto.MenuPage;
import dev.thiagooliveira.delivery.menus.mappers.MenuMapperImpl;
import dev.thiagooliveira.delivery.menus.respositories.MenuItemRepository;
import dev.thiagooliveira.delivery.restaurants.clients.RestaurantAdminApi;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {
    private MenuService menuService;

    @Mock
    private RestaurantAdminApiFactory restaurantAdminApiFactory;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    void setUp() {
        this.menuService = new MenuServiceImpl(restaurantAdminApiFactory, new MenuMapperImpl(), menuItemRepository);
    }

    @Test
    @DisplayName("get a menu by id.")
    void getByIdTest() {
        dev.thiagooliveira.delivery.menus.model.MenuItem menuItem = getMenu(UUID.randomUUID(), UUID.randomUUID());
        when(menuItemRepository.findById(eq(menuItem.getId()))).thenReturn(Optional.of(menuItem));
        Optional<MenuItem> result = this.menuService.getById(menuItem.getId());
        assertTrue(result.isPresent());
        MenuItem resultItem = result.get();
        assertEquals(menuItem.getRestaurantId(), resultItem.getRestaurantId());
        assertEquals(menuItem.getId(), resultItem.getId());
        assertEquals(menuItem.getDescription(), resultItem.getDescription());
        assertEquals(menuItem.getName(), resultItem.getName());
        assertEquals(menuItem.getPrice(), resultItem.getPrice());

        result = this.menuService.getById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("save a menu.")
    void saveTest() {
        MenuItem menuItemDto = getMenuDto(UUID.randomUUID(), UUID.randomUUID());
        RestaurantAdminApi restaurantAdminApi = mock(RestaurantAdminApi.class);
        when(restaurantAdminApiFactory.create()).thenReturn(restaurantAdminApi);
        when(menuItemRepository.save(any(dev.thiagooliveira.delivery.menus.model.MenuItem.class)))
                .thenAnswer(i -> i.getArgument(0));
        MenuItem menuItemDtoSaved = this.menuService.save(menuItemDto);
        assertEquals(menuItemDto.getId(), menuItemDtoSaved.getId());
        assertEquals(menuItemDto.getRestaurantId(), menuItemDtoSaved.getRestaurantId());
        assertEquals(menuItemDto.getName(), menuItemDtoSaved.getName());
        assertEquals(menuItemDto.getDescription(), menuItemDtoSaved.getDescription());
        assertEquals(menuItemDto.getPrice(), menuItemDtoSaved.getPrice());
    }

    @Test
    @DisplayName("get all menu from restaurant.")
    void getAll() {
        dev.thiagooliveira.delivery.menus.model.MenuItem menuItem = getMenu(UUID.randomUUID(), UUID.randomUUID());
        List<dev.thiagooliveira.delivery.menus.model.MenuItem> content = List.of(menuItem);
        var page = new PageImpl<>(content, PageRequest.of(0, 1), content.size());
        when(menuItemRepository.findByRestaurantId(eq(menuItem.getRestaurantId()), any(PageRequest.class)))
                .thenReturn(page);
        MenuPage menuPageResult = this.menuService.getAll(
                menuItem.getRestaurantId(),
                new dev.thiagooliveira.delivery.menus.dto.PageRequest()
                        .pageNumber(0)
                        .pageSize(10));
        assertTrue(menuPageResult.getFirst());
        assertTrue(menuPageResult.getLast());
        assertEquals(0, menuPageResult.getNumber());
        assertEquals(1, menuPageResult.getTotalPages());
        assertEquals(1, menuPageResult.getNumberOfElements());
        assertEquals(1, menuPageResult.getContent().size());
        MenuItem item = menuPageResult.getContent().get(0);
        assertEquals(menuItem.getId(), item.getId());
        assertEquals(menuItem.getRestaurantId(), item.getRestaurantId());
        assertEquals(menuItem.getName(), item.getName());
        assertEquals(menuItem.getDescription(), item.getDescription());
        assertEquals(menuItem.getPrice(), item.getPrice());
    }
}
