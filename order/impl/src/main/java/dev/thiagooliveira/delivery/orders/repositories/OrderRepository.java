package dev.thiagooliveira.delivery.orders.repositories;

import dev.thiagooliveira.delivery.orders.model.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, UUID> {
    Optional<Order> findById(UUID id);

    Order save(Order order);
}
