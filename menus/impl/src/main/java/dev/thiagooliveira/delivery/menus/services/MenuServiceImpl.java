package dev.thiagooliveira.delivery.menus.services;

import dev.thiagooliveira.delivery.menus.dto.MenuItem;
import dev.thiagooliveira.delivery.menus.dto.MenuPage;
import dev.thiagooliveira.delivery.menus.dto.PageRequest;
import dev.thiagooliveira.delivery.menus.mappers.MenuMapper;
import dev.thiagooliveira.delivery.menus.respositories.MenuItemRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final MenuItemRepository menuItemRepository;

    @Override
    public Optional<MenuItem> getById(UUID id) {
        return menuItemRepository.findById(id).map(menuMapper::toMenuItem);
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        return menuMapper.toMenuItem(menuItemRepository.save(menuMapper.toMenuItem(menuItem)));
    }

    @Override
    public MenuPage getAll(UUID restaurantId, PageRequest pageRequest) {
        return menuMapper.toMenuPage(menuItemRepository.findByRestaurantId(
                restaurantId,
                org.springframework.data.domain.PageRequest.of(
                        pageRequest.getPageNumber(), pageRequest.getPageSize())));
    }
}
