package dev.thiagooliveira.delivery.orders.config.factories;

import dev.thiagooliveira.delivery.users.clients.UsersAdminApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class UsersApiFactory {
    private final ApplicationContext applicationContext;

    public UsersAdminApi create(){
        return applicationContext.getBean(UsersAdminApi.class);
    }
}
