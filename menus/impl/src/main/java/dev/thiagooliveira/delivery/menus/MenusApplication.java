package dev.thiagooliveira.delivery.menus;

import dev.thiagooliveira.delivery.menus.model.MenuItem;
import dev.thiagooliveira.delivery.menus.services.MenuItemService;
import dev.thiagooliveira.delivery.restaurants.spec.ApiClient;
import dev.thiagooliveira.delivery.restaurants.spec.client.RestaurantApi;
import dev.thiagooliveira.delivery.restaurants.spec.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.spec.dto.RestaurantPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
public class MenusApplication {
    public static void main(String[] args) {
        SpringApplication.run(MenusApplication.class, args);
    }

    private RestaurantApi restaurantApi;

    @Autowired
    private MenuItemService menuItemService;
    @Bean
    public CommandLineRunner importData() {
        this.restaurantApi = new ApiClient().setBasePath("http://localhost:8082").buildClient(RestaurantApi.class);
        return args -> {
            RestaurantPage page = restaurantApi.getRestaurants(0, 9999);
            page.getContent().forEach(r -> {
                saveMenuItem(r.getId(), "Classic Cheeseburger", "Juicy beef patty with cheddar cheese, lettuce, tomato, and pickles.", BigDecimal.valueOf(12.50));
                saveMenuItem(r.getId(), "Margherita Pizza", "Classic pizza with tomato sauce, mozzarella cheese, and fresh basil.", BigDecimal.valueOf(10.00));
                saveMenuItem(r.getId(), "Caesar Salad", "Crisp romaine lettuce with Caesar dressing, croutons, and parmesan cheese.", BigDecimal.valueOf(8.75));
                saveMenuItem(r.getId(), "Spaghetti Carbonara", "Pasta with creamy egg and cheese sauce, pancetta, and black pepper.", BigDecimal.valueOf(14.00));
                saveMenuItem(r.getId(), "BBQ Ribs", "Tender pork ribs glazed with smoky BBQ sauce, served with coleslaw.", BigDecimal.valueOf(16.00));
                saveMenuItem(r.getId(), "Margherita Burger", "Beef patty topped with fresh mozzarella, tomatoes, and basil pesto.", BigDecimal.valueOf(13.00));
                saveMenuItem(r.getId(), "Pepperoni Pizza", "Pizza topped with spicy pepperoni slices and mozzarella cheese.", BigDecimal.valueOf(11.50));
                saveMenuItem(r.getId(), "Greek Salad", "Mixed greens with olives, feta cheese, cucumbers, and red onions.", BigDecimal.valueOf(9.00));
                saveMenuItem(r.getId(), "Chicken Alfredo", "Pasta with creamy Alfredo sauce and grilled chicken breast.", BigDecimal.valueOf(15.00));
                saveMenuItem(r.getId(), "Tiramisu", "Classic Italian dessert with layers of coffee-soaked ladyfingers and mascarpone cheese.", BigDecimal.valueOf(7.50));
            });
        };
    }

    private void saveMenuItem(String restaurantId, String name, String description, BigDecimal price) {
        var menuItem = new MenuItem();
        menuItem.setRestaurantId(UUID.fromString(restaurantId));
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);

        menuItemService.save(menuItem);
    }
}