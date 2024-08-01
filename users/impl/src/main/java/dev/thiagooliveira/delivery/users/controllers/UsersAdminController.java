package dev.thiagooliveira.delivery.users.controllers;

import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.services.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersAdminController implements UsersAdminApi {

    private final UserService userService;

    @Override
    public ResponseEntity<User> getUserByIdAsAdmin(UUID id) {
        return ResponseEntity.ok(userService.get(id));
    }
}
