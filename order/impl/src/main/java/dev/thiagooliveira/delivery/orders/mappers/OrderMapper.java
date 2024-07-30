package dev.thiagooliveira.delivery.orders.mappers;

import dev.thiagooliveira.delivery.orders.dto.OrderDetails;
import dev.thiagooliveira.delivery.orders.dto.OrderPage;
import dev.thiagooliveira.delivery.orders.model.Order;
import dev.thiagooliveira.delivery.orders.model.OrderEvent;
import dev.thiagooliveira.delivery.orders.model.OrderItem;
import dev.thiagooliveira.delivery.orders.model.OrderItemId;
import dev.thiagooliveira.delivery.orders.validators.handlers.OrderValidatorResult;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@Mapper
public interface OrderMapper {

    OrderDetails toOrderDetails(Order order);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "userId", source = "user.id")
    dev.thiagooliveira.delivery.orders.dto.Order toOrder(Order order);

    OrderPage toOrderPage(org.springframework.data.domain.Page<dev.thiagooliveira.delivery.orders.model.Order> page);

    Order toOrder(OrderValidatorResult orderValidated);

    @Mapping(target = "status", source = "id.status")
    dev.thiagooliveira.delivery.orders.dto.OrderEvent orderEventToOrderEvent(OrderEvent orderEvent);

    @Mapping(target = "id.itemId", source = "id")
    dev.thiagooliveira.delivery.orders.model.OrderItem orderItemToOrderItem(
            dev.thiagooliveira.delivery.orders.dto.OrderItem orderItem);

    default UUID map(OrderItemId value) {
        if (value == null) {
            return null;
        }
        return value.getItemId();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Order order) {
        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }
        var sum = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
            sum = sum.add(item.getPrice().multiply(new BigDecimal(item.getAmount())));
        }
        order.setTotal(sum);

        if (order.getEvents() == null) {
            order.setEvents(new ArrayList<>());
        }
    }

    default PageImpl map(OrderPage page) {
        return new PageImpl<>(
                page.getContent(),
                PageRequest.of(
                        page.getPageable().getPageNumber(), page.getPageable().getPageSize()),
                page.getTotalElements());
    }

    dev.thiagooliveira.delivery.orders.model.Address map(dev.thiagooliveira.delivery.orders.dto.Address address);
}
