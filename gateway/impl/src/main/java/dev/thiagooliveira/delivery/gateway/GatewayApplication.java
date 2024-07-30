package dev.thiagooliveira.delivery.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users-service", r -> r.path("/users-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://users-service"))
                .route("restaurants-service", r -> r.path("/restaurants-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://restaurants-service"))
                .route("menus-service", r -> r.path("/menus-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://menus-service"))
                .route("orders-service", r -> r.path("/orders-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://orders-service"))
                .build();
    }
}
