package dev.thiagooliveira.delivery.orders.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEventId {
    @NotNull
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;
}
