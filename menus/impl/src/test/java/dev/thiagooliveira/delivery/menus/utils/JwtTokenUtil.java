package dev.thiagooliveira.delivery.menus.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

public class JwtTokenUtil {
    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor createUserToken() {
        return SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor createServiceToken() {
        return SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("ROLE_SERVICE"));
    }
}
