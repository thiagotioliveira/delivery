package dev.thiagooliveira.delivery.users.controllers;

import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.CreateAddress;
import dev.thiagooliveira.delivery.users.dto.UpdateAddress;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.exceptions.UserNotFoundException;
import dev.thiagooliveira.delivery.users.services.AddressService;
import dev.thiagooliveira.delivery.users.services.UserService;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UserService userService;
    private final AddressService addressService;

    @Override
    public ResponseEntity<Void> deleteUserAddress(UUID userId, UUID addressId) {
        validateRequest(userId);
        addressService.delete(userId, addressId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Address>> getUserAddress(UUID userId) {
        validateRequest(userId);
        return ResponseEntity.ok(addressService.getByUserId(userId));
    }

    @Override
    public ResponseEntity<User> getUserById(UUID userId) {
        validateRequest(userId);
        return ResponseEntity.ok(userService.getById(userId).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public ResponseEntity<Void> patchUserAddress(UUID userId, UUID addressId, UpdateAddress updateAddress) {
        validateRequest(userId);
        addressService.update(userId, addressId, updateAddress);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> postUserAddress(UUID userId, CreateAddress createAddress) {
        validateRequest(userId);
        addressService.save(userId, createAddress);
        return ResponseEntity.noContent().build();
    }

    private void validateRequest(UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(userId.toString(), authentication.getName())) {
            throw new UserNotFoundException();
        }
    }
}
