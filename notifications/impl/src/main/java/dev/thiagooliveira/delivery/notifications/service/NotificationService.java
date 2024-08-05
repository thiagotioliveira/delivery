package dev.thiagooliveira.delivery.notifications.service;

import dev.thiagooliveira.delivery.notifications.dto.Notification;

public interface NotificationService {

    void send(Notification notification);
}
