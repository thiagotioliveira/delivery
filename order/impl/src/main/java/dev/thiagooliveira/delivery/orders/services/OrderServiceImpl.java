package dev.thiagooliveira.delivery.orders.services;

import static dev.thiagooliveira.delivery.orders.model.OrderStatus.*;

import dev.thiagooliveira.delivery.orders.exceptions.OrderNotFound;
import dev.thiagooliveira.delivery.orders.mappers.OrderMapper;
import dev.thiagooliveira.delivery.orders.repositories.OrderRepository;
import dev.thiagooliveira.delivery.orders.spec.dto.*;
import dev.thiagooliveira.delivery.orders.validators.OrderValidator;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderValidator orderValidator;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public Optional<OrderDetails> getById(UUID id) {
        return orderRepository.findById(id).map(orderMapper::toOrderDetails);
    }

    @Override
    public OrderDetails create(CreateOrder createOrder) {
        dev.thiagooliveira.delivery.orders.model.Order order =
                orderMapper.toOrder(orderValidator.validate(createOrder));
        // TODO - apply payment here
        order.setStatusPending();
        return orderMapper.toOrderDetails(orderRepository.save(order));
    }

    @Override
    public OrderDetails approve(UUID orderId) {
        dev.thiagooliveira.delivery.orders.model.Order order =
                orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);
        order.setStatusApproved();
        return orderMapper.toOrderDetails(orderRepository.save(order));
    }

    @Override
    public OrderDetails deliver(UUID orderId) {
        dev.thiagooliveira.delivery.orders.model.Order order =
                orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);
        order.setStatusDelivered();
        return orderMapper.toOrderDetails(orderRepository.save(order));
    }

    @Override
    public OrderPage getAll(PageRequest pageRequest) {
        return orderMapper.toOrderPage(orderRepository.findAll(org.springframework.data.domain.PageRequest.of(
                pageRequest.getPageNumber(), pageRequest.getPageSize())));
    }
}
