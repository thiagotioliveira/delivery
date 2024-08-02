package dev.thiagooliveira.delivery.location.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**", "GET"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**", "GET"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/admin/**"))
                        .hasAnyRole("ADMIN", "SERVICE")
                        .requestMatchers(new AntPathRequestMatcher("/**"))
                        .hasRole("USER")
                        .anyRequest()
                        .authenticated());
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new JWTConverter())));
        return http.build();
    }
}
