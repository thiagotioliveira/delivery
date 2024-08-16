package dev.thiagooliveira.delivery.orders.services;

import static dev.thiagooliveira.delivery.orders.validators.handlers.OrderValidatedMap.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.delivery.orders.dto.*;
import dev.thiagooliveira.delivery.orders.exceptions.OrderNotFound;
import dev.thiagooliveira.delivery.orders.mappers.OrderMapper;
import dev.thiagooliveira.delivery.orders.mappers.OrderMapperImpl;
import dev.thiagooliveira.delivery.orders.model.Order;
import dev.thiagooliveira.delivery.orders.producers.OrderUpdatedProducer;
import dev.thiagooliveira.delivery.orders.repositories.OrderRepository;
import dev.thiagooliveira.delivery.orders.validators.OrderValidator;
import dev.thiagooliveira.delivery.orders.validators.handlers.OrderValidatorResult;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderUpdatedProducer orderUpdatedProducer;

    private OrderMapper orderMapper = new OrderMapperImpl();
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        this.orderService = new OrderServiceImpl(orderValidator, orderMapper, orderRepository, orderUpdatedProducer);
    }

    @Test
    @DisplayName("test getById with existing order")
    void getById_whenOrderExists_shouldReturnOrderDetails() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findByIdAndUserId(orderId, userId)).thenReturn(Optional.of(order));

        Optional<OrderDetails> result = orderService.getById(userId, orderId);

        assertTrue(result.isPresent());
        assertEquals(orderId, result.get().getId());
        verify(orderRepository, times(1)).findByIdAndUserId(orderId, userId);
    }

    @Test
    @DisplayName("test getById with non-existing order")
    void getById_whenOrderDoesNotExist_shouldReturnEmpty() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findByIdAndUserId(orderId, userId)).thenReturn(Optional.empty());

        Optional<OrderDetails> result = orderService.getById(userId, orderId);

        assertFalse(result.isPresent());
        verify(orderRepository, times(1)).findByIdAndUserId(orderId, userId);
    }

    @Test
    @DisplayName("test create order")
    void create_shouldSaveOrderAndReturnOrderDetails() {
        CreateOrder createOrder = new CreateOrder();
        Order order = new Order();
        OrderDetails orderDetails = new OrderDetails();
        Map<String, Object> map = Map.of(
                KEY_RESTAURANT, new Restaurant().id(UUID.randomUUID()),
                KEY_USER, new User().id(UUID.randomUUID()),
                KEY_ITEMS, List.of(new OrderItem().id(UUID.randomUUID()).price(BigDecimal.TEN)));
        OrderValidatorResult result = new OrderValidatorResult(map);

        when(orderValidator.validate(createOrder)).thenReturn(result);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderDetails orderDetailsResult = orderService.create(createOrder);

        assertNotNull(orderDetailsResult);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderUpdatedProducer, times(1)).send(any(OrderDetails.class));
    }

    @Test
    @DisplayName("test approve order with existing order")
    void approve_whenOrderExists_shouldUpdateStatusAndReturnOrderDetails() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setStatus(dev.thiagooliveira.delivery.orders.model.OrderStatus.PENDING);
        dev.thiagooliveira.delivery.orders.model.OrderEvent pendingEvent =
                new dev.thiagooliveira.delivery.orders.model.OrderEvent();
        order.setEvents(new ArrayList<>(List.of(pendingEvent)));
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        OrderDetails result = orderService.approve(userId, orderId);

        assertNotNull(result);
        assertEquals(OrderStatus.APPROVED, result.getStatus());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
        verify(orderUpdatedProducer, times(1)).send(any(OrderDetails.class));
    }

    @Test
    @DisplayName("test approve order with non-existing order")
    void approve_whenOrderDoesNotExist_shouldThrowOrderNotFoundException() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFound.class, () -> orderService.approve(userId, orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    @DisplayName("test deliver order with existing order")
    void deliver_whenOrderExists_shouldUpdateStatusAndReturnOrderDetails() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(dev.thiagooliveira.delivery.orders.model.OrderStatus.APPROVED);
        dev.thiagooliveira.delivery.orders.model.OrderEvent event =
                new dev.thiagooliveira.delivery.orders.model.OrderEvent();
        order.setEvents(new ArrayList<>(List.of(event)));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        OrderDetails result = orderService.deliver(userId, orderId);

        assertNotNull(result);
        assertEquals(OrderStatus.DELIVERED, result.getStatus());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
        verify(orderUpdatedProducer, times(1)).send(any(OrderDetails.class));
    }

    @Test
    @DisplayName("test deliver order with non-existing order")
    void deliver_whenOrderDoesNotExist_shouldThrowOrderNotFoundException() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFound.class, () -> orderService.deliver(userId, orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    @DisplayName("test getAll orders")
    void getAll_shouldReturnOrderPage() {
        UUID userId = UUID.randomUUID();
        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Order.desc("createdAt")));
        Order order = new Order();
        Page<Order> page = new PageImpl<>(
                List.of(order), PageRequest.of(0, 10, org.springframework.data.domain.Sort.by("createdAt")), 1);

        when(orderRepository.findByUserId(userId, pageRequest)).thenReturn(page);

        OrderPage result = orderService.getAll(
                userId,
                new dev.thiagooliveira.delivery.orders.dto.PageRequest()
                        .pageNumber(0)
                        .pageSize(10));

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(orderRepository, times(1)).findByUserId(userId, pageRequest);
    }
}
