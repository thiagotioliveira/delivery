package dev.thiagooliveira.delivery.users.services;

import dev.thiagooliveira.delivery.users.clients.IAMClient;
import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import dev.thiagooliveira.delivery.users.producers.UserAddessUpdatedProducer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IAMClient iamClient;
    private final UserMapper userMapper;
    private final UserAddessUpdatedProducer userAddessUpdatedProducer;

    @Override
    public User get(UUID id) {
        return iamClient.get(id);
    }

    @Override
    public void updateAddress(UUID id, Address address) {
        User user = iamClient.updateAddress(id, address);
        userAddessUpdatedProducer.send(userMapper.toUserAddress(user));
    }
}
