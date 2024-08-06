package dev.thiagooliveira.delivery.users.repositories;

import dev.thiagooliveira.delivery.users.model.Address;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, UUID> {

    List<Address> findByUserIdOrderByDescription(UUID userId);

    boolean existsByUserIdAndDescription(UUID userId, String description);

    void deleteByIdAndUserId(UUID id, UUID userId);

    long countByUserId(UUID userId);
}
