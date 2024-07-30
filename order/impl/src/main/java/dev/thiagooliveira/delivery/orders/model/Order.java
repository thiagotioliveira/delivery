package dev.thiagooliveira.delivery.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "restaurant_order")
@Data
@ToString(exclude = {"user", "restaurant", "items", "events"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    private BigDecimal total;

    @Embedded
    @NotNull
    private Restaurant restaurant;

    @Embedded
    @NotNull
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEvent> events;

    public void setStatusPending() {
        if (this.status != null || this.events == null) {
            throw new IllegalStateException("cannot change the status.");
        }
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.events.add(OrderEvent.builder()
                .id(OrderEventId.builder().status(this.getStatus()).build())
                .dateTime(createdAt)
                .order(this)
                .build());
    }

    public void setStatusApproved() {
        if (!OrderStatus.PENDING.equals(this.status) || this.events == null) {
            throw new IllegalStateException("cannot change the status.");
        }
        this.status = OrderStatus.APPROVED;
        this.events.add(OrderEvent.builder()
                .id(OrderEventId.builder().status(this.getStatus()).build())
                .dateTime(LocalDateTime.now())
                .order(this)
                .build());
    }
    public void setStatusDelivered() {
        if (!OrderStatus.APPROVED.equals(this.status) || this.events == null) {
            throw new IllegalStateException("cannot change the status.");
        }
        this.status = OrderStatus.DELIVERED;
        this.events.add(OrderEvent.builder()
                .id(OrderEventId.builder().status(this.getStatus()).build())
                .dateTime(LocalDateTime.now())
                .order(this)
                .build());
    }
}
