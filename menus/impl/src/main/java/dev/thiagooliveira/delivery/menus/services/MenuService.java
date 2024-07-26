package dev.thiagooliveira.delivery.menus.services;

import dev.thiagooliveira.delivery.menus.spec.dto.MenuItem;
import dev.thiagooliveira.delivery.menus.spec.dto.MenuPage;
import dev.thiagooliveira.delivery.menus.spec.dto.PageRequest;
import java.util.Optional;
import java.util.UUID;

public interface MenuService {

    Optional<MenuItem> getById(UUID id);

    MenuItem save(MenuItem menuItem);

    MenuPage getAll(UUID restaurantId, PageRequest pageRequest);
}
