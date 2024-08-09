package dev.thiagooliveira.delivery.restaurants;

import dev.thiagooliveira.delivery.restaurants.model.Address;
import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;

import java.util.Objects;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class RestaurantsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantsApplication.class, args);
    }

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Bean
    public CommandLineRunner importData() {
        return args -> {
            if (restaurantRepository.findAll(PageRequest.of(0, 1)).getTotalElements() > 0) {
                return;
            }
            var restaurant = new Restaurant();
            restaurant.setName("Restaurant A");
            restaurant.setDescription("A great place to eat.");
            restaurant.setPhoneNumber("+351 210 123 456");
            restaurant.setAddress(createAddress("Rua Augusta", "24", "Lisbon", "Lisbon", "1100-053", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant B");
            restaurant.setDescription("Wonderful food.");
            restaurant.setPhoneNumber("+351 210 654 321");
            restaurant.setAddress(
                    createAddress("Avenida da Liberdade", "35", "Lisbon", "Lisbon", "1250-096", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant C");
            restaurant.setDescription("Excellent dishes.");
            restaurant.setPhoneNumber("+351 220 123 654");
            restaurant.setAddress(
                    createAddress("Rua dos Douradores", "18", "Lisbon", "Lisbon", "1100-207", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant D");
            restaurant.setDescription("Pleasant atmosphere.");
            restaurant.setPhoneNumber("+351 210 987 654");
            restaurant.setAddress(createAddress("Rua Garrett", "50", "Lisbon", "Lisbon", "1200-203", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant E");
            restaurant.setDescription("Great service.");
            restaurant.setPhoneNumber("+351 210 765 432");
            restaurant.setAddress(createAddress("Rua da Prata", "11", "Lisbon", "Lisbon", "1100-414", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant F");
            restaurant.setDescription("Delicious meals.");
            restaurant.setPhoneNumber("+351 210 876 543");
            restaurant.setAddress(createAddress("Rua do Carmo", "40", "Lisbon", "Lisbon", "1200-093", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant G");
            restaurant.setDescription("International cuisine.");
            restaurant.setPhoneNumber("+351 210 345 678");
            restaurant.setAddress(createAddress("Rua de São Paulo", "22", "Lisbon", "Lisbon", "1200-429", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant H");
            restaurant.setDescription("Family-friendly.");
            restaurant.setPhoneNumber("+351 210 234 567");
            restaurant.setAddress(createAddress("Rua da Conceição", "15", "Lisbon", "Lisbon", "1100-151", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant I");
            restaurant.setDescription("Traditional dishes.");
            restaurant.setPhoneNumber("+351 210 543 210");
            restaurant.setAddress(createAddress("Rua de Santa Justa", "5", "Lisbon", "Lisbon", "1100-483", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant J");
            restaurant.setDescription("Variety of dishes.");
            restaurant.setPhoneNumber("+351 210 654 987");
            restaurant.setAddress(createAddress("Rua do Ouro", "30", "Lisbon", "Lisbon", "1100-061", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant K");
            restaurant.setDescription("Modern and cozy.");
            restaurant.setPhoneNumber("+351 210 123 111");
            restaurant.setAddress(
                    createAddress("Rua de São Nicolau", "12", "Lisbon", "Lisbon", "1100-547", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant L");
            restaurant.setDescription("Authentic flavors.");
            restaurant.setPhoneNumber("+351 210 123 222");
            restaurant.setAddress(createAddress("Rua da Madalena", "20", "Lisbon", "Lisbon", "1100-321", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant M");
            restaurant.setDescription("Exquisite dining experience.");
            restaurant.setPhoneNumber("+351 210 123 333");
            restaurant.setAddress(createAddress("Rua de São Julião", "5", "Lisbon", "Lisbon", "1100-524", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant N");
            restaurant.setDescription("Best seafood in town.");
            restaurant.setPhoneNumber("+351 210 123 444");
            restaurant.setAddress(createAddress("Rua do Comércio", "15", "Lisbon", "Lisbon", "1100-150", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant O");
            restaurant.setDescription("Charming and elegant.");
            restaurant.setPhoneNumber("+351 210 123 555");
            restaurant.setAddress(
                    createAddress("Rua dos Correeiros", "23", "Lisbon", "Lisbon", "1100-061", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant P");
            restaurant.setDescription("Fine dining.");
            restaurant.setPhoneNumber("+351 210 123 666");
            restaurant.setAddress(createAddress("Rua da Vitória", "8", "Lisbon", "Lisbon", "1100-619", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant Q");
            restaurant.setDescription("Cozy and casual.");
            restaurant.setPhoneNumber("+351 210 123 777");
            restaurant.setAddress(createAddress("Rua de São Mamede", "18", "Lisbon", "Lisbon", "1100-535", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant R");
            restaurant.setDescription("Delicious vegetarian options.");
            restaurant.setPhoneNumber("+351 210 123 888");
            restaurant.setAddress(createAddress("Rua do Alecrim", "27", "Lisbon", "Lisbon", "1200-014", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant S");
            restaurant.setDescription("Rustic charm.");
            restaurant.setPhoneNumber("+351 210 123 999");
            restaurant.setAddress(createAddress("Rua das Flores", "30", "Lisbon", "Lisbon", "1200-195", "Portugal"));
            restaurantRepository.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant T");
            restaurant.setDescription("Fusion cuisine.");
            restaurant.setPhoneNumber("+351 210 123 000");
            restaurant.setAddress(createAddress("Rua da Palmeira", "35", "Lisbon", "Lisbon", "1200-304", "Portugal"));
            restaurantRepository.save(restaurant);
            log.debug("restaurants created.");
        };
    }

    private static Address createAddress(
            String street, String number, String city, String state, String postalCode, String country) {
        Address address = new Address();
        address.setStreet(street);
        address.setNumber(number);
        address.setCity(city);
        address.setState(state);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        address.setLatitude(0d);
        address.setLongitude(0d);
        address.setFormatted(format(address));
        return address;
    }

    public static String format(Address address) {
        return String.format(
                        "%s, %s, %s, %s, %s, %s, %s",
                        Objects.requireNonNullElse(address.getStreet(), ""),
                        Objects.requireNonNullElse(address.getNumber(), ""),
                        Objects.requireNonNullElse(address.getNotes(), ""),
                        Objects.requireNonNullElse(address.getCity(), ""),
                        Objects.requireNonNullElse(address.getState(), ""),
                        Objects.requireNonNullElse(address.getPostalCode(), ""),
                        Objects.requireNonNullElse(address.getCountry(), ""))
                .replaceAll(", ,", "")
                .trim();
    }
}
