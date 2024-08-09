package dev.thiagooliveira.delivery.users.services;

import dev.thiagooliveira.delivery.users.dto.User;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> getById(UUID userId);

    Optional<User> getByUsername(String username);
}
