package dev.thiagooliveira.delivery.users.message.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserAddress implements Serializable {
    private UUID userId;

    private Address address;
}
