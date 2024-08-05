package dev.thiagooliveira.delivery.notifications.service;

import dev.thiagooliveira.delivery.notifications.dto.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void send(Notification notification) {
        log.debug("sending notification to the user: {}", notification);
    }
}
