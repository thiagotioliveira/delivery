package dev.thiagooliveira.delivery.menus.controllers;

import dev.thiagooliveira.delivery.menus.exceptions.InvalidItemException;
import dev.thiagooliveira.delivery.menus.exceptions.ItemNotFoundException;
import dev.thiagooliveira.delivery.menus.services.MenuService;
import dev.thiagooliveira.delivery.menus.spec.api.MenuApi;
import dev.thiagooliveira.delivery.menus.spec.dto.MenuItem;
import dev.thiagooliveira.delivery.menus.spec.dto.MenuPage;
import dev.thiagooliveira.delivery.menus.spec.dto.PageRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController implements MenuApi {

    private final MenuService menuService;

    @Override
    public ResponseEntity<MenuItem> getItemByRestaurantIdAndItemId(UUID restaurantId, UUID itemId) {
        MenuItem menuItem = menuService.getById(itemId).orElseThrow(ItemNotFoundException::new);
        if (!restaurantId.equals(menuItem.getRestaurantId())) {
            throw new InvalidItemException();
        }
        return ResponseEntity.ok(menuItem);
    }

    @Override
    public ResponseEntity<MenuPage> getItemsByRestaurantId(UUID restaurantId, Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(menuService.getAll(
                restaurantId, new PageRequest().pageNumber(pageNumber).pageSize(pageSize)));
    }
}
