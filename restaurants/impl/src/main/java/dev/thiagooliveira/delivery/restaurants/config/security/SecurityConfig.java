package dev.thiagooliveira.delivery.restaurants.config.security;

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
        http
                //                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
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
        //        http.cors(cors -> cors.disable());
        return http.build();
    }
    /*
       @Bean
       public UrlBasedCorsConfigurationSource corsConfigurationSource() {
           CorsConfiguration configuration = new CorsConfiguration();
           configuration.addAllowedOrigin("http://localhost:8081"); // Você pode especificar uma origem específica ou usar "*" para todas as origens
           configuration.addAllowedMethod("*"); // Permite todos os métodos (GET, POST, etc.)
           configuration.addAllowedHeader("*"); // Permite todos os cabeçalhos

           UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
           source.registerCorsConfiguration("/**", configuration);
           return source;
       }

    */
}
