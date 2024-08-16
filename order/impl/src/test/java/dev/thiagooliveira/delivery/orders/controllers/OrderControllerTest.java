package dev.thiagooliveira.delivery.orders.controllers;

import static dev.thiagooliveira.delivery.orders.utils.JwtTokenUtil.createUserToken;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.delivery.orders.config.security.SecurityConfig;
import dev.thiagooliveira.delivery.orders.dto.*;
import dev.thiagooliveira.delivery.orders.services.OrderService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = OrderController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@Import(SecurityConfig.class)
class OrderControllerTest {

    @MockBean
    private RequestContextManager requestContextManager;

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {}

    @Test
    void approve() throws Exception {
        mockMvc.perform(patch("/orders/{orderId}/approve", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(createUserToken()))
                .andExpect(status().isNotImplemented());
    }

    @Test
    void approveWithoutPrivilege() throws Exception {
        mockMvc.perform(patch("/orders/{orderId}/approve", UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createOrder() throws Exception {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(UUID.randomUUID());
        when(orderService.create(any(CreateOrder.class))).thenReturn(orderDetails);

        CreateOrder createOrder = new CreateOrder()
                .restaurantId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .total(BigDecimal.TEN)
                .items(List.of(new OrderItem()
                        .price(BigDecimal.TEN)
                        .id(UUID.randomUUID())
                        .name("item")
                        .amount(1)));
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(createUserToken())
                        .content(objectMapper.writeValueAsString(createOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(orderDetails.getId().toString()));
    }

    @Test
    void deliver() throws Exception {
        mockMvc.perform(patch("/orders/{orderId}/deliver", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(createUserToken()))
                .andExpect(status().isNotImplemented());
    }

    @Test
    void getOrderById() throws Exception {
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(UUID.randomUUID());
        when(orderService.getById(any(UUID.class), any(UUID.class))).thenReturn(Optional.of(new OrderDetails()));
        mockMvc.perform(get("/orders/{orderId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(createUserToken()))
                .andExpect(status().isOk());
    }

    @Test
    void getOrders() throws Exception {
        when(requestContextManager.getUserAuthenticatedId()).thenReturn(UUID.randomUUID());
        when(orderService.getAll(any(UUID.class), any(PageRequest.class))).thenReturn(new OrderPage());
        mockMvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON).with(createUserToken()))
                .andExpect(status().isOk());
    }
}
