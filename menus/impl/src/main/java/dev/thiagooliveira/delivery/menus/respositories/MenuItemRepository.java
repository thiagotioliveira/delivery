package dev.thiagooliveira.delivery.menus.respositories;

import dev.thiagooliveira.delivery.menus.model.MenuItem;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MenuItemRepository extends PagingAndSortingRepository<MenuItem, UUID> {
    MenuItem save(MenuItem item);

    Optional<MenuItem> findById(UUID id);

    Page<MenuItem> findByRestaurantId(UUID restaurantId, Pageable pageable);
}
