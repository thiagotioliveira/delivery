package dev.thiagooliveira.delivery.orders.config.factories;

import dev.thiagooliveira.delivery.menus.clients.MenuApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class MenuApiFactory {
    private final ApplicationContext applicationContext;

    public MenuApi create(){
        return applicationContext.getBean(MenuApi.class);
    }
}
