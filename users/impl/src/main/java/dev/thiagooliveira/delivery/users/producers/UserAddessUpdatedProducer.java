package dev.thiagooliveira.delivery.users.producers;

import dev.thiagooliveira.delivery.users.message.dto.UserAddress;

public interface UserAddessUpdatedProducer {
    void send(UserAddress user);
}
