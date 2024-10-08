package dev.thiagooliveira.delivery.menus.config;

import dev.thiagooliveira.delivery.menus.config.factories.RestaurantAdminApiFactory;
import dev.thiagooliveira.delivery.menus.dto.MenuItem;
import dev.thiagooliveira.delivery.menus.dto.MenuPage;
import dev.thiagooliveira.delivery.menus.dto.PageRequest;
import dev.thiagooliveira.delivery.menus.services.MenuService;
import dev.thiagooliveira.delivery.restaurants.dto.RestaurantPage;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DumpConfig {

    @Bean
    public CommandLineRunner importData(RestaurantAdminApiFactory factory, MenuService menuService) {
        return args -> {
            try {
                var restaurantAdminApi = factory.create();
                RestaurantPage page = restaurantAdminApi.getRestaurantsAsAdmin(0, 9999);
                page.getContent().forEach(r -> {
                    MenuPage menuPage = menuService.getAll(
                            r.getId(), new PageRequest().pageNumber(0).pageSize(1));
                    if (menuPage.getContent().size() > 0) {
                        return;
                    }
                    menuService.save(buildItem(
                            r.getId(),
                            "Classic Cheeseburger",
                            "Juicy beef patty with cheddar cheese, lettuce, tomato, and pickles.",
                            BigDecimal.valueOf(12.50)));
                    menuService.save(buildItem(
                            r.getId(),
                            "Margherita Pizza",
                            "Classic pizza with tomato sauce, mozzarella cheese, and fresh basil.",
                            BigDecimal.valueOf(10.00)));
                    menuService.save(buildItem(
                            r.getId(),
                            "Caesar Salad",
                            "Crisp romaine lettuce with Caesar dressing, croutons, and parmesan cheese.",
                            BigDecimal.valueOf(8.75)));
                    menuService.save(buildItem(
                            r.getId(),
                            "Spaghetti Carbonara",
                            "Pasta with creamy egg and cheese sauce, pancetta, and black pepper.",
                            BigDecimal.valueOf(14.00)));
                    menuService.save(buildItem(
                            r.getId(),
                            "BBQ Ribs",
                            "Tender pork ribs glazed with smoky BBQ sauce, served with coleslaw.",
                            BigDecimal.valueOf(16.00)));
                    menuService.save(buildItem(
                            r.getId(),
                            "Margherita Burger",
                            "Beef patty topped with fresh mozzarella, tomatoes, and basil pesto.",
                            BigDecimal.valueOf(13.00)));
                    menuService.save(buildItem(
                            r.getId(),
                            "Pepperoni Pizza",
                            "Pizza topped with spicy pepperoni slices and mozzarella cheese.",
                            BigDecimal.valueOf(11.50)));
                    menuService.save(buildItem(
                            r.getId(),
                            "Greek Salad",
                            "Mixed greens with olives, feta cheese, cucumbers, and red onions.",
                            BigDecimal.valueOf(9.00)));
                    menuService.save(buildItem(
                            r.getId(),
                            "Chicken Alfredo",
                            "Pasta with creamy Alfredo sauce and grilled chicken breast.",
                            BigDecimal.valueOf(15.00)));
                    menuService.save(buildItem(
                            r.getId(),
                            "Tiramisu",
                            "Classic Italian dessert with layers of coffee-soaked ladyfingers and mascarpone cheese.",
                            BigDecimal.valueOf(7.50)));
                });
                log.debug("items saved");
            } catch (Exception e) {
                log.debug("Unable to connect to restaurant-service.", e);
            }
        };
    }

    private MenuItem buildItem(UUID restaurantId, String name, String description, BigDecimal price) {
        var menuItem = new MenuItem();
        menuItem.setRestaurantId(restaurantId);
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        return menuItem;
    }
}
