package dev.thiagooliveira.delivery.users.controllers;

import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import dev.thiagooliveira.delivery.users.services.IAMService;
import dev.thiagooliveira.users.spec.api.UsersApi;
import dev.thiagooliveira.users.spec.dto.Address;
import dev.thiagooliveira.users.spec.dto.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final IAMService iamService;

    private final UserMapper userMapper;

    @Override
    public ResponseEntity<Void> addUserAddress(UUID id, Address address) {
        iamService.updateAddress(id, address);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<User> getUserById(UUID id) {
        return ResponseEntity.ok(iamService.get(id));
    }
}
