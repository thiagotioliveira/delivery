package dev.thiagooliveira.delivery.orders.services;

import dev.thiagooliveira.delivery.orders.dto.*;
import dev.thiagooliveira.delivery.orders.exceptions.OrderNotFound;
import dev.thiagooliveira.delivery.orders.mappers.OrderMapper;
import dev.thiagooliveira.delivery.orders.producers.OrderUpdatedProducer;
import dev.thiagooliveira.delivery.orders.repositories.OrderRepository;
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
    private final OrderUpdatedProducer orderUpdatedProducer;

    @Override
    public Optional<OrderDetails> getById(UUID userId, UUID id) {
        return orderRepository.findByIdAndUserId(id, userId).map(orderMapper::toOrderDetails);
    }

    @Override
    public OrderDetails create(CreateOrder createOrder) {
        dev.thiagooliveira.delivery.orders.model.Order order =
                orderMapper.toOrder(orderValidator.validate(createOrder));
        // TODO - apply payment here
        order.setStatusPending();
        var orderDetails = orderMapper.toOrderDetails(orderRepository.save(order));
        orderUpdatedProducer.send(orderDetails);
        return orderDetails;
    }

    @Override
    public OrderDetails approve(UUID userId, UUID orderId) {
        dev.thiagooliveira.delivery.orders.model.Order order =
                orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);
        order.setStatusApproved();
        var orderDetails = orderMapper.toOrderDetails(orderRepository.save(order));
        orderUpdatedProducer.send(orderDetails);
        return orderDetails;
    }

    @Override
    public OrderDetails deliver(UUID userId, UUID orderId) {
        dev.thiagooliveira.delivery.orders.model.Order order =
                orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);
        order.setStatusDelivered();
        var orderDetails = orderMapper.toOrderDetails(orderRepository.save(order));
        orderUpdatedProducer.send(orderDetails);
        return orderDetails;
    }

    @Override
    public OrderPage getAll(UUID userId, PageRequest pageRequest) {
        return orderMapper.toOrderPage(orderRepository.findByUserId(
                userId,
                org.springframework.data.domain.PageRequest.of(
                        pageRequest.getPageNumber(),
                        pageRequest.getPageSize(),
                        org.springframework.data.domain.Sort.by(
                                org.springframework.data.domain.Sort.Order.desc("createdAt")))));
    }
}
