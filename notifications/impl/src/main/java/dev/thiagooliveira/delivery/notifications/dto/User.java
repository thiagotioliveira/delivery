package dev.thiagooliveira.delivery.notifications.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private UUID id;
    private String name;
    private String email;
}
