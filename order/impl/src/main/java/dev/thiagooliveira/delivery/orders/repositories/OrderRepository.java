package dev.thiagooliveira.delivery.orders.repositories;

import dev.thiagooliveira.delivery.orders.model.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, UUID> {
    Optional<Order> findById(UUID id);

    Optional<Order> findByIdAndUserId(UUID id, UUID userId);

    Page<Order> findByUserId(UUID userId, Pageable pageable);

    Order save(Order order);
}
