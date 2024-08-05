package dev.thiagooliveira.delivery.notifications.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Notification {
    private User user;
    private String message;
}
