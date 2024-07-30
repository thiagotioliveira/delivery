package dev.thiagooliveira.delivery.orders.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemId {

    @NotNull
    private UUID orderId;

    @NotNull
    private UUID itemId;
}
