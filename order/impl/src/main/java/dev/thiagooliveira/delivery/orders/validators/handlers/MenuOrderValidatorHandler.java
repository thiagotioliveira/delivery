package dev.thiagooliveira.delivery.orders.validators.handlers;

import dev.thiagooliveira.delivery.menus.clients.MenuApi;
import dev.thiagooliveira.delivery.menus.dto.MenuItem;
import dev.thiagooliveira.delivery.orders.dto.CreateOrder;
import dev.thiagooliveira.delivery.orders.dto.OrderItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MenuOrderValidatorHandler implements OrderValidatorHandler {

    private final MenuApi menuApi;

    @Override
    public OrderValidatedMap validate(CreateOrder createOrder) {
        Map<UUID, MenuItem> itemsMap =
                menuApi
                        .getItemsByRestaurantId(createOrder.getRestaurantId(), 0, Integer.MAX_VALUE)
                        .getContent()
                        .stream()
                        .filter(item -> createOrder.getItems().stream()
                                .map(OrderItem::getId)
                                .collect(Collectors.toList())
                                .contains(item.getId()))
                        .collect(Collectors.toMap(MenuItem::getId, item -> item));

        if (itemsMap.size() != createOrder.getItems().size()) {
            // TODO
        }
        List<OrderItem> items = new ArrayList<>();
        for (OrderItem orderItem : createOrder.getItems()) {
            MenuItem menuItem = itemsMap.get(orderItem.getId());
            if (!menuItem.getPrice().equals(orderItem.getPrice())) {
                // TODO
            }
            if (!menuItem.getName().equals(orderItem.getName())) {
                log.debug("item name is different for item id '{}', updated.", orderItem.getId());
                orderItem.setName(menuItem.getName());
            }
            items.add(orderItem);
            log.debug("item {} validated.", orderItem.getId());
        }
        var output = new OrderValidatedMap();
        output.putItems(items);
        return output;
    }
}
