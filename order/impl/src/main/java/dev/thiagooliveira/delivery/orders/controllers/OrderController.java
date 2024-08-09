package dev.thiagooliveira.delivery.orders.controllers;

import dev.thiagooliveira.delivery.orders.dto.*;
import dev.thiagooliveira.delivery.orders.exceptions.OrderNotFound;
import dev.thiagooliveira.delivery.orders.services.OrderService;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderDetails> approve(UUID id) {
        throw new NotImplementedException();
        //        return ResponseEntity.ok(orderService.approve(id));
    }

    @Override
    public ResponseEntity<OrderDetails> createOrder(CreateOrder createOrder) {
        OrderDetails orderDetails = orderService.create(createOrder);
        return ResponseEntity.created(URI.create("/orders/" + orderDetails.getId()))
                .body(orderDetails);
    }

    @Override
    public ResponseEntity<OrderDetails> deliver(UUID id) {
        throw new NotImplementedException();
        //        return ResponseEntity.ok(orderService.deliver(id));
    }

    @Override
    public ResponseEntity<OrderDetails> getOrderById(UUID id) {
        return ResponseEntity.ok(orderService.getById(getUserId(), id).orElseThrow(OrderNotFound::new));
    }

    @Override
    public ResponseEntity<OrderPage> getOrders(Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(orderService.getAll(
                getUserId(), new PageRequest().pageNumber(pageNumber).pageSize(pageSize)));
    }

    private static UUID getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }
}
