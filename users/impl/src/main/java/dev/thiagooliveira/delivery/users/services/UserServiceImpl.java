package dev.thiagooliveira.delivery.users.services;

import dev.thiagooliveira.delivery.location.clients.LocationApi;
import dev.thiagooliveira.delivery.users.clients.IAMClient;
import dev.thiagooliveira.delivery.users.config.factories.LocationApiFactory;
import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.mappers.AddressMapper;
import dev.thiagooliveira.delivery.users.mappers.UserMapper;
import dev.thiagooliveira.delivery.users.producers.UserAddessUpdatedProducer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IAMClient iamClient;
    private final LocationApiFactory locationApiFactory;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final UserAddessUpdatedProducer userAddessUpdatedProducer;

    @Override
    public User get(UUID id) {
        return iamClient.get(id);
    }

    @Override
    public void updateAddress(UUID id, Address address) {
        LocationApi locationApi = locationApiFactory.create();
        User user = iamClient.updateAddress(
                id, addressMapper.toAddressValidated(locationApi.validateAddress(addressMapper.toAddress(address))));
        userAddessUpdatedProducer.send(userMapper.toUserAddress(user));
    }
}
