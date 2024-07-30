package dev.thiagooliveira.delivery.orders.services;

import static dev.thiagooliveira.delivery.orders.model.OrderStatus.*;

import dev.thiagooliveira.delivery.notifications.message.dto.EmailCommand;
import dev.thiagooliveira.delivery.orders.exceptions.OrderNotFound;
import dev.thiagooliveira.delivery.orders.mappers.OrderMapper;
import dev.thiagooliveira.delivery.orders.producers.EmailProducer;
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
    private final EmailProducer emailProducer;

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
        var orderDetails = orderMapper.toOrderDetails(orderRepository.save(order));
        emailProducer.send(EmailCommand.builder()
                .emailTo(orderDetails.getUser().getEmail())
                .title("Order created successfully!")
                .content(String.format(
                        "Your order number %s has been created and is awaiting approval.", orderDetails.getId()))
                .build());
        return orderDetails;
    }

    @Override
    public OrderDetails approve(UUID orderId) {
        dev.thiagooliveira.delivery.orders.model.Order order =
                orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);
        order.setStatusApproved();
        var orderDetails = orderMapper.toOrderDetails(orderRepository.save(order));
        emailProducer.send(EmailCommand.builder()
                .emailTo(orderDetails.getUser().getEmail())
                .title("Order approved with successfully!")
                .content(String.format(
                        "Your order number %s has been approved and and is being prepared by the restaurant.",
                        orderDetails.getId()))
                .build());
        return orderDetails;
    }

    @Override
    public OrderDetails deliver(UUID orderId) {
        dev.thiagooliveira.delivery.orders.model.Order order =
                orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);
        order.setStatusDelivered();
        var orderDetails = orderMapper.toOrderDetails(orderRepository.save(order));
        emailProducer.send(EmailCommand.builder()
                .emailTo(orderDetails.getUser().getEmail())
                .title("Order delivered with successfully!")
                .content(String.format(
                        "Your order number %s has been delivered. We hope you like it.", orderDetails.getId()))
                .build());
        return orderDetails;
    }

    @Override
    public OrderPage getAll(PageRequest pageRequest) {
        return orderMapper.toOrderPage(orderRepository.findAll(org.springframework.data.domain.PageRequest.of(
                pageRequest.getPageNumber(), pageRequest.getPageSize())));
    }
}
