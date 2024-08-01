package dev.thiagooliveira.delivery.users.controllers;

import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.exceptions.UserNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import dev.thiagooliveira.delivery.users.services.UserService;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    private final UserMapper userMapper;

    @Override
    public ResponseEntity<Void> addUserAddress(UUID id, Address address) {
        validateRequest(id);
        userService.updateAddress(id, address);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<User> getUserById(UUID id) {
        validateRequest(id);
        return ResponseEntity.ok(userService.get(id));
    }

    private void validateRequest(UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(userId.toString(), authentication.getName())) {
            throw new UserNotFoundException();
        }
    }
}
