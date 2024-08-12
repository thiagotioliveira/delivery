package dev.thiagooliveira.delivery.menus.fixtures;

import dev.thiagooliveira.delivery.menus.model.MenuItem;
import java.math.BigDecimal;
import java.util.UUID;

public class Fixtures {

    private static final String MENU_ITEM_NAME = "BBQ Ribs";
    private static final String MENU_ITEM_DESCRIPTION =
            "Tender pork ribs glazed with smoky BBQ sauce, served with coleslaw.";

    private static final BigDecimal MENU_ITEM_PRICE = BigDecimal.TEN;

    public static MenuItem getMenu(UUID restaurantId, UUID menuId) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(MENU_ITEM_NAME);
        menuItem.setDescription(MENU_ITEM_DESCRIPTION);
        menuItem.setId(menuId);
        menuItem.setPrice(MENU_ITEM_PRICE);
        return menuItem;
    }

    public static dev.thiagooliveira.delivery.menus.dto.MenuItem getMenuDto(UUID restaurantId, UUID menuId) {
        return new dev.thiagooliveira.delivery.menus.dto.MenuItem()
                .id(menuId)
                .restaurantId(restaurantId)
                .name(MENU_ITEM_NAME)
                .description(MENU_ITEM_DESCRIPTION)
                .price(MENU_ITEM_PRICE);
    }
}
