package dev.thiagooliveira.delivery.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int amount;
}
