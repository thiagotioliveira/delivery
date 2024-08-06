package dev.thiagooliveira.delivery.users.services;

import dev.thiagooliveira.delivery.users.clients.IAMClient;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.dto.UserWithAddressId;
import dev.thiagooliveira.delivery.users.exceptions.AddressNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IAMClient iamClient;
    private final AddressService addressService;
    private final UserMapper userMapper;

    @Override
    public Optional<User> getById(UUID userId) {
        Optional<UserWithAddressId> userOptional = iamClient.get(userId);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        UserWithAddressId userWithAddressId = userOptional.get();
        if (userWithAddressId.getCurrentAddressId() != null) {
            return Optional.of(userMapper.toUser(
                    userWithAddressId,
                    addressService
                            .getById(userWithAddressId.getCurrentAddressId())
                            .orElseThrow(AddressNotFoundException::new)));
        } else {
            return Optional.of(userMapper.toUser(userWithAddressId));
        }
    }
}
