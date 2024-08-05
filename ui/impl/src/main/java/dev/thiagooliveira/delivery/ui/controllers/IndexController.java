package dev.thiagooliveira.delivery.ui.controllers;

import dev.thiagooliveira.delivery.ui.config.properties.AppProperties;
import dev.thiagooliveira.delivery.ui.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final AppProperties appProperties;
    private final UserMapper userMapper;

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OidcUser principal) {
        ModelAndView model = new ModelAndView("index");
        var user = userMapper.toUser(principal);
        model.addObject("user", user);
        model.addObject("gateway", appProperties.getClient().getGateway().getBaseUrl());
        return model;
    }
}
