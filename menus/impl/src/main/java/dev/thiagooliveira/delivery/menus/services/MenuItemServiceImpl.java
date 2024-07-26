package dev.thiagooliveira.delivery.menus.services;

import dev.thiagooliveira.delivery.menus.model.MenuItem;
import dev.thiagooliveira.delivery.menus.respositories.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    @Override
    public MenuItem save(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }
}
