package dev.thiagooliveira.delivery.users;

import dev.thiagooliveira.delivery.users.dto.CreateAddress;
import dev.thiagooliveira.delivery.users.dto.User;
import dev.thiagooliveira.delivery.users.exceptions.UserNotFoundException;
import dev.thiagooliveira.delivery.users.services.AddressService;
import dev.thiagooliveira.delivery.users.services.UserService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class UsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Bean
    public CommandLineRunner importData() {
        return args -> {
            User user1 = userService.getByUsername("michael.smith").orElseThrow(UserNotFoundException::new);
            if (Objects.isNull(user1.getAddress())) {
                addressService.save(
                        user1.getId(),
                        new CreateAddress()
                                .description("Office")
                                .street("Rua João de Freitas Branco")
                                .number("34B")
                                .notes("")
                                .city("Lisbon")
                                .state("Lisbon")
                                .postalCode("1500-714")
                                .country("Portugal"));

                addressService.save(
                        user1.getId(),
                        new CreateAddress()
                                .description("Home")
                                .street("Rua Alexandre Herculano")
                                .number("2")
                                .notes("")
                                .city("Lisbon")
                                .state("Lisbon")
                                .postalCode("1150-006")
                                .country("Portugal"));
                log.debug("saving default addresses to {}", user1.getEmail());
            }
            User user2 = userService.getByUsername("laura.jones").orElseThrow(UserNotFoundException::new);
            if (Objects.isNull(user2.getAddress())) {
                addressService.save(
                        user2.getId(),
                        new CreateAddress()
                                .description("Office")
                                .street("Av. 24 de Julho")
                                .number("171A")
                                .notes("")
                                .city("Lisbon")
                                .state("Lisbon")
                                .postalCode("1350-352")
                                .country("Portugal"));
                addressService.save(
                        user2.getId(),
                        new CreateAddress()
                                .description("Home")
                                .street("Av. Mar. Gomes da Costa")
                                .number("29")
                                .notes("B1")
                                .city("Lisbon")
                                .state("Lisbon")
                                .postalCode("1800-255")
                                .country("Portugal"));
                log.debug("saving default addresses to {}", user2.getEmail());
            }
        };
    }
}
