package dev.thiagooliveira.delivery.menus.services;

import dev.thiagooliveira.delivery.menus.config.factories.RestaurantAdminApiFactory;
import dev.thiagooliveira.delivery.menus.dto.MenuItem;
import dev.thiagooliveira.delivery.menus.dto.MenuPage;
import dev.thiagooliveira.delivery.menus.dto.PageRequest;
import dev.thiagooliveira.delivery.menus.mappers.MenuMapper;
import dev.thiagooliveira.delivery.menus.respositories.MenuItemRepository;
import dev.thiagooliveira.delivery.restaurants.clients.RestaurantAdminApi;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final RestaurantAdminApiFactory restaurantAdminApiFactory;
    private final MenuMapper menuMapper;
    private final MenuItemRepository menuItemRepository;

    @Override
    public Optional<MenuItem> getById(UUID id) {
        return menuItemRepository.findById(id).map(menuMapper::toMenuItem);
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        RestaurantAdminApi restaurantAdminApi = restaurantAdminApiFactory.create();
        restaurantAdminApi.getRestaurantByIdAsAdmin(menuItem.getRestaurantId());
        MenuItem menuItemSaved = menuMapper.toMenuItem(menuItemRepository.save(menuMapper.toMenuItem(menuItem)));
        log.debug("item '{}' saved", menuItemSaved.getId());
        return menuItemSaved;
    }

    @Override
    public MenuPage getAll(UUID restaurantId, PageRequest pageRequest) {
        return menuMapper.toMenuPage(menuItemRepository.findByRestaurantId(
                restaurantId,
                org.springframework.data.domain.PageRequest.of(
                        pageRequest.getPageNumber(), pageRequest.getPageSize())));
    }
}
