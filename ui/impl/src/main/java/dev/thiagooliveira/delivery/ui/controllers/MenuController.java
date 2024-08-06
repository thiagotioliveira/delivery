package dev.thiagooliveira.delivery.ui.controllers;

import dev.thiagooliveira.delivery.ui.config.properties.AppProperties;
import dev.thiagooliveira.delivery.ui.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final AppProperties appProperties;
    private final UserMapper userMapper;

    @GetMapping("/restaurants/{restaurantId}/menu")
    public ModelAndView settings(@AuthenticationPrincipal OidcUser principal, @PathVariable String restaurantId) {
        ModelAndView model = new ModelAndView("menu");
        var user = userMapper.toUser(principal);
        model.addObject("user", user);
        model.addObject("gateway", appProperties.getClient().getGateway().getBaseUrl());
        model.addObject("restaurantId", restaurantId);
        return model;
    }
}
