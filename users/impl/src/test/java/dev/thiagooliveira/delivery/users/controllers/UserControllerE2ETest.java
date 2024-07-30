package dev.thiagooliveira.delivery.users.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import dev.thiagooliveira.users.spec.ApiClient;
import dev.thiagooliveira.users.spec.client.UsersApi;
import dev.thiagooliveira.users.spec.dto.Address;
import dev.thiagooliveira.users.spec.dto.ApiResponse;
import dev.thiagooliveira.users.spec.dto.User;
import feign.FeignException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class UserControllerE2ETest {
    private static final String USER_ID = "2f3f2318-98b0-42e4-8d13-62aa8999099f";

    @LocalServerPort
    private int port;

    private static PostgreSQLContainer postgres;

    private static RabbitMQContainer rabbit;

    private static KeycloakContainer keycloak;

    private static Network network;

    private UsersApi usersApi;

    @BeforeAll
    public static void setUp() {
        buildEnvironment();
    }

    @AfterAll
    public static void tearDown() {
        if (keycloak != null) {
            keycloak.stop();
        }
        if (postgres != null) {
            postgres.stop();
        }
        if (rabbit != null) {
            rabbit.stop();
        }
        if (network != null) {
            network.close();
        }
    }

    @BeforeEach
    public void beforeEach() {
        this.usersApi = new ApiClient().setBasePath("http://localhost:" + port).buildClient(UsersApi.class);
    }

    private static void buildEnvironment() {
        network = Network.newNetwork();
        postgres = (PostgreSQLContainer) new PostgreSQLContainer("postgres:15.3")
                .withUsername("postgres")
                .withPassword("admin")
                .withDatabaseName("delivery_db")
                .withInitScript("postgres/init.sql")
                .withNetwork(network)
                .withNetworkAliases("postgres");
        postgres.setPortBindings(List.of("5432:5432"));
        postgres.start();

        rabbit = new RabbitMQContainer("rabbitmq:3.13-management")
                .withNetwork(network)
                .withNetworkAliases("rabbitmq");
        rabbit.setPortBindings(List.of("15672:15672", "5672:5672"));
        rabbit.start();

        keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:23.0.6")
                .withEnv("KC_HOSTNAME", "localhost")
                .withEnv("KC_HOSTNAME_PORT", "8080")
                .withEnv("KC_HOSTNAME_STRICT_BACKCHANNEL", "false")
                .withEnv("KC_HTTP_ENABLED", "true")
                .withEnv("KC_HOSTNAME_STRICT_HTTPS", "false")
                .withEnv("KC_HEALTH_ENABLED", "true")
                .withEnv("KEYCLOAK_ADMIN", "admin")
                .withEnv("KEYCLOAK_ADMIN_PASSWORD", "admin")
                .withEnv("KC_DB", "postgres")
                .withEnv("KC_DB_URL", "jdbc:postgresql://postgres:5432/keycloak_db")
                .withEnv("KC_DB_USERNAME", "keycloak")
                .withEnv("KC_DB_PASSWORD", "keycloak")
                .withRealmImportFiles("keycloak/delivery-realm.json", "keycloak/delivery-users-0.json")
                .dependsOn(postgres)
                .withNetwork(network)
                .waitingFor(new HttpWaitStrategy()
                        .forPort(8080)
                        .forStatusCode(200)
                        .forPath("/health/started")
                        .withStartupTimeout(Duration.ofMinutes(2)))
                .withNetworkAliases("keycloak");
        keycloak.setPortBindings(List.of("8760:8080"));
        keycloak.start();
    }

    @Test
    void shouldGetUserById() {
        final UUID userId = UUID.fromString(USER_ID);
        ApiResponse<User> response = usersApi.getUserByIdWithHttpInfo(userId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        User user = response.getData();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getFirstName()).isNotNull();
        assertThat(user.getLastName()).isNotNull();
        assertThat(user.getEmail()).isNotNull();
        assertThat(user.getAddress()).isNull();
    }

    @Test
    void shouldGetNotFoundWhenUserIdInvalid() {
        final UUID userId = UUID.randomUUID();
        try {
            ApiResponse<User> response = usersApi.getUserByIdWithHttpInfo(userId);
            assertThat(response).isNotNull();
        } catch (FeignException.NotFound e) {
            assertThat(e.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }
    }

    @Test
    void shouldUpdateAddress() {
        final UUID userId = UUID.fromString(USER_ID);
        Address address = new Address();
        address.setStreet("Rua da Liberdade");
        address.setNumber("123");
        address.setNotes("Apto 4B");
        address.setCity("Lisbon");
        address.setState("Lisbon");
        address.setPostalCode("1250-001");
        address.setCountry("Portugal");

        ApiResponse<Void> response = usersApi.addUserAddressWithHttpInfo(userId, address);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
