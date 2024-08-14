package dev.thiagooliveira.delivery.users.services;

import dev.thiagooliveira.delivery.location.clients.LocationApi;
import dev.thiagooliveira.delivery.users.config.factories.LocationApiFactory;
import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.CreateAddress;
import dev.thiagooliveira.delivery.users.dto.UpdateAddress;
import dev.thiagooliveira.delivery.users.exceptions.AddressException;
import dev.thiagooliveira.delivery.users.exceptions.AddressNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.AddressMapper;
import dev.thiagooliveira.delivery.users.producers.UserAddessUpdatedProducer;
import dev.thiagooliveira.delivery.users.repositories.AddressRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
// TODO - refactor this class
public class AddressServiceImpl implements AddressService {
    private final IAMService iamService;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final LocationApiFactory locationApiFactory;
    private final UserAddessUpdatedProducer userAddessUpdatedProducer;

    @Override
    public Optional<Address> getById(UUID addressId) {
        return addressRepository.findById(addressId).map(addressMapper::toAddress);
    }

    @Override
    @Transactional
    public Address save(UUID userId, CreateAddress createAddress) {
        if (addressRepository.existsByUserIdAndDescription(userId, createAddress.getDescription())) {
            throw new AddressException("address description already exists.");
        }
        addressRepository.findByUserIdOrderByDescription(userId).forEach(a -> {
            a.setCurrent(false);
            addressRepository.save(a);
        });
        LocationApi locationApi = locationApiFactory.create();
        Address address = addressMapper.toAddress(addressRepository.save(addressMapper.toAddress(
                userId,
                createAddress.getDescription(),
                true,
                locationApi.validateAddress(addressMapper.toAddress(createAddress)))));
        iamService.updateCurrentAddress(userId, address.getId());
        userAddessUpdatedProducer.send(addressMapper.toUserAddress(userId, address));
        return address;
    }

    @Override
    @Transactional
    public void delete(UUID userId, UUID addressId) {
        Map<UUID, dev.thiagooliveira.delivery.users.model.Address> map =
                addressRepository.findByUserIdOrderByDescription(userId).stream()
                        .collect(Collectors.toMap(dev.thiagooliveira.delivery.users.model.Address::getId, a -> a));
        dev.thiagooliveira.delivery.users.model.Address address = map.get(addressId);
        if (address == null) {
            throw new AddressNotFoundException();
        }
        addressRepository.deleteByIdAndUserId(addressId, userId);
        if (address.isCurrent()) {
            Optional<dev.thiagooliveira.delivery.users.model.Address> any = map.values().stream()
                    .filter(a -> !a.getId().equals(addressId))
                    .findAny();
            if (any.isPresent()) {
                dev.thiagooliveira.delivery.users.model.Address newAddressCurrent = any.get();
                newAddressCurrent.setCurrent(true);
                addressRepository.save(newAddressCurrent);
                iamService.updateCurrentAddress(userId, newAddressCurrent.getId());
                userAddessUpdatedProducer.send(
                        addressMapper.toUserAddress(userId, addressMapper.toAddress(newAddressCurrent)));
            } else {
                throw new AddressException("you need at least one registered address.");
            }
        }
    }

    @Override
    public List<Address> getByUserId(UUID userId) {
        return addressRepository.findByUserIdOrderByDescription(userId).stream()
                .map(addressMapper::toAddress)
                .collect(Collectors.toList());
    }

    @Override
    public void update(UUID userId, UUID addressId, UpdateAddress updateAddress) {
        Map<UUID, dev.thiagooliveira.delivery.users.model.Address> map =
                addressRepository.findByUserIdOrderByDescription(userId).stream()
                        .collect(Collectors.toMap(dev.thiagooliveira.delivery.users.model.Address::getId, a -> a));
        dev.thiagooliveira.delivery.users.model.Address address = map.get(addressId);
        if (address == null) {
            throw new AddressNotFoundException();
        }
        if (updateAddress.getCurrent()) {
            if (!address.isCurrent()) {
                map.values().stream().filter(a -> !a.getId().equals(addressId)).forEach(a -> {
                    a.setCurrent(false);
                    addressRepository.save(a);
                });
                address.setCurrent(true);
                addressRepository.save(address);
                iamService.updateCurrentAddress(userId, address.getId());
                userAddessUpdatedProducer.send(addressMapper.toUserAddress(userId, addressMapper.toAddress(address)));
            }
        } else {
            if (address.isCurrent()) {
                address.setCurrent(false);
                addressRepository.save(address);
                Optional<dev.thiagooliveira.delivery.users.model.Address> any = map.values().stream()
                        .filter(a -> !a.getId().equals(addressId))
                        .findAny();
                if (any.isPresent()) {
                    dev.thiagooliveira.delivery.users.model.Address newAddressCurrent = any.get();
                    newAddressCurrent.setCurrent(true);
                    addressRepository.save(newAddressCurrent);
                    iamService.updateCurrentAddress(userId, newAddressCurrent.getId());
                    userAddessUpdatedProducer.send(
                            addressMapper.toUserAddress(userId, addressMapper.toAddress(newAddressCurrent)));
                } else {
                    throw new AddressException("you need at least one registered address.");
                }
            }
        }
    }
}
