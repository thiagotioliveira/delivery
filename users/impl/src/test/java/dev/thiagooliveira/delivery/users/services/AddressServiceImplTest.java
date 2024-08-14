package dev.thiagooliveira.delivery.users.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.delivery.location.clients.LocationApi;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.users.config.factories.LocationApiFactory;
import dev.thiagooliveira.delivery.users.dto.Address;
import dev.thiagooliveira.delivery.users.dto.CreateAddress;
import dev.thiagooliveira.delivery.users.dto.UpdateAddress;
import dev.thiagooliveira.delivery.users.exceptions.AddressException;
import dev.thiagooliveira.delivery.users.exceptions.AddressNotFoundException;
import dev.thiagooliveira.delivery.users.mappers.AddressMapperImpl;
import dev.thiagooliveira.delivery.users.producers.UserAddessUpdatedProducer;
import dev.thiagooliveira.delivery.users.repositories.AddressRepository;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {
    @Mock
    private IAMService iamService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private LocationApiFactory locationApiFactory;

    @Mock
    private UserAddessUpdatedProducer userAddessUpdatedProducer;

    @Mock
    private LocationApi locationApi;

    private AddressService addressService;

    @BeforeEach
    void setUp() {
        this.addressService = new AddressServiceImpl(
                iamService, addressRepository, new AddressMapperImpl(), locationApiFactory, userAddessUpdatedProducer);
    }

    @Test
    @DisplayName("should return Address when address is found by ID")
    void getById() {
        UUID addressId = UUID.randomUUID();
        dev.thiagooliveira.delivery.users.model.Address addressModel =
                new dev.thiagooliveira.delivery.users.model.Address();
        addressModel.setId(addressId);
        Address address = new Address();

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(addressModel));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(addressModel));

        Optional<Address> result = addressService.getById(addressId);

        assertTrue(result.isPresent());
        assertEquals(addressId, result.get().getId());
        verify(addressRepository).findById(addressId);
    }

    @Test
    @DisplayName("should throw AddressException when address description already exists")
    void save_addressDescriptionAlreadyExists() {
        UUID userId = UUID.randomUUID();
        CreateAddress createAddress = new CreateAddress();
        createAddress.setDescription("Home");

        when(addressRepository.existsByUserIdAndDescription(userId, createAddress.getDescription()))
                .thenReturn(true);

        assertThrows(AddressException.class, () -> addressService.save(userId, createAddress));
    }

    @Test
    @DisplayName("should save new address and update current address")
    void save() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        CreateAddress createAddress = new CreateAddress();
        createAddress.setDescription("Home");
        AddressValidated validatedAddress = new AddressValidated();
        dev.thiagooliveira.delivery.users.model.Address addressModel =
                new dev.thiagooliveira.delivery.users.model.Address();
        addressModel.setId(addressId);

        when(addressRepository.existsByUserIdAndDescription(userId, createAddress.getDescription()))
                .thenReturn(false);
        when(locationApiFactory.create()).thenReturn(locationApi);
        when(locationApi.validateAddress(any())).thenReturn(validatedAddress);
        when(addressRepository.save(any(dev.thiagooliveira.delivery.users.model.Address.class)))
                .thenReturn(addressModel);

        Address savedAddress = addressService.save(userId, createAddress);

        assertNotNull(savedAddress);
        assertEquals(addressId, savedAddress.getId());
        verify(addressRepository, times(1)).save(any(dev.thiagooliveira.delivery.users.model.Address.class));
        verify(iamService, times(1)).updateCurrentAddress(userId, addressId);
        verify(userAddessUpdatedProducer, times(1)).send(any());
    }

    @Test
    @DisplayName("should delete address and update current address if necessary")
    void delete() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UUID addressId2 = UUID.randomUUID();
        dev.thiagooliveira.delivery.users.model.Address addressModel =
                new dev.thiagooliveira.delivery.users.model.Address();
        addressModel.setId(addressId);
        addressModel.setCurrent(true);
        dev.thiagooliveira.delivery.users.model.Address addressModel2 =
                new dev.thiagooliveira.delivery.users.model.Address();
        addressModel2.setId(addressId2);
        addressModel2.setCurrent(false);
        Map<UUID, dev.thiagooliveira.delivery.users.model.Address> addresses = new HashMap<>();
        addresses.put(addressId, addressModel);
        addresses.put(addressId2, addressModel2);

        when(addressRepository.findByUserIdOrderByDescription(userId)).thenReturn(new ArrayList<>(addresses.values()));

        addressService.delete(userId, addressId);

        verify(addressRepository, times(1)).deleteByIdAndUserId(addressId, userId);
        verify(iamService, times(1)).updateCurrentAddress(eq(userId), any());
        verify(userAddessUpdatedProducer, times(1)).send(any());
    }

    @Test
    @DisplayName("should throw AddressNotFoundException when address is not found by ID")
    void delete_addressNotFound() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        when(addressRepository.findByUserIdOrderByDescription(userId)).thenReturn(Collections.emptyList());

        assertThrows(AddressNotFoundException.class, () -> addressService.delete(userId, addressId));
    }

    @Test
    @DisplayName("should return list of addresses by user ID")
    void getByUserId() {
        UUID userId = UUID.randomUUID();
        dev.thiagooliveira.delivery.users.model.Address addressModel1 =
                new dev.thiagooliveira.delivery.users.model.Address();
        dev.thiagooliveira.delivery.users.model.Address addressModel2 =
                new dev.thiagooliveira.delivery.users.model.Address();
        List<dev.thiagooliveira.delivery.users.model.Address> addressModels = List.of(addressModel1, addressModel2);

        when(addressRepository.findByUserIdOrderByDescription(userId)).thenReturn(addressModels);

        List<Address> result = addressService.getByUserId(userId);

        assertEquals(2, result.size());
        verify(addressRepository).findByUserIdOrderByDescription(userId);
    }

    @Test
    @DisplayName("should update the address as current if necessary")
    void update() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UpdateAddress updateAddress = new UpdateAddress();
        updateAddress.setCurrent(true);

        dev.thiagooliveira.delivery.users.model.Address addressModel =
                new dev.thiagooliveira.delivery.users.model.Address();
        addressModel.setId(addressId);
        addressModel.setCurrent(false);

        Map<UUID, dev.thiagooliveira.delivery.users.model.Address> addressMap = Map.of(addressId, addressModel);

        when(addressRepository.findByUserIdOrderByDescription(userId)).thenReturn(new ArrayList<>(addressMap.values()));

        addressService.update(userId, addressId, updateAddress);

        assertTrue(addressModel.isCurrent());
        verify(iamService, times(1)).updateCurrentAddress(userId, addressId);
        verify(userAddessUpdatedProducer, times(1)).send(any());
    }

    @Test
    @DisplayName("should throw AddressNotFoundException when trying to update non-existent address")
    void update_addressNotFound() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UpdateAddress updateAddress = new UpdateAddress();

        when(addressRepository.findByUserIdOrderByDescription(userId)).thenReturn(Collections.emptyList());

        assertThrows(AddressNotFoundException.class, () -> addressService.update(userId, addressId, updateAddress));
    }
}
