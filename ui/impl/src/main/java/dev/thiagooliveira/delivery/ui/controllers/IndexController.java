package dev.thiagooliveira.delivery.ui.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OidcUser principal){
        ModelAndView model = new ModelAndView("index");
        model.addObject("username", principal.getAttribute("name"));
        model.addObject("useremail", principal.getAttribute("email"));
        return model;
    }
}
