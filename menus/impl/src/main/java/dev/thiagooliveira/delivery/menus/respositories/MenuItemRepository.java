package dev.thiagooliveira.delivery.menus.respositories;

import dev.thiagooliveira.delivery.menus.model.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MenuItemRepository extends CrudRepository<MenuItem, UUID> {
}
