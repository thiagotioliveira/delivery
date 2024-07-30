package dev.thiagooliveira.delivery.restaurants;

import dev.thiagooliveira.delivery.restaurants.dto.PageRequest;
import dev.thiagooliveira.delivery.restaurants.model.Address;
import dev.thiagooliveira.delivery.restaurants.model.Restaurant;
import dev.thiagooliveira.delivery.restaurants.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class RestaurantsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantsApplication.class, args);
    }

    @Autowired
    private RestaurantService restaurantService;

    @Bean
    public CommandLineRunner importData() {
        return args -> {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNumber(0);
            pageRequest.setPageSize(1);
            if (restaurantService.getAll(pageRequest).getTotalElements() > 0) {
                return;
            }
            var restaurant = new Restaurant();
            restaurant.setName("Restaurant A");
            restaurant.setDescription("A great place to eat.");
            restaurant.setPhoneNumber("+351 210 123 456");
            restaurant.setAddress(createAddress("Rua Augusta", "24", "Lisboa", "Lisboa", "1100-053", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant B");
            restaurant.setDescription("Wonderful food.");
            restaurant.setPhoneNumber("+351 210 654 321");
            restaurant.setAddress(
                    createAddress("Avenida da Liberdade", "35", "Lisboa", "Lisboa", "1250-096", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant C");
            restaurant.setDescription("Excellent dishes.");
            restaurant.setPhoneNumber("+351 220 123 654");
            restaurant.setAddress(
                    createAddress("Rua dos Douradores", "18", "Lisboa", "Lisboa", "1100-207", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant D");
            restaurant.setDescription("Pleasant atmosphere.");
            restaurant.setPhoneNumber("+351 210 987 654");
            restaurant.setAddress(createAddress("Rua Garrett", "50", "Lisboa", "Lisboa", "1200-203", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant E");
            restaurant.setDescription("Great service.");
            restaurant.setPhoneNumber("+351 210 765 432");
            restaurant.setAddress(createAddress("Rua da Prata", "11", "Lisboa", "Lisboa", "1100-414", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant F");
            restaurant.setDescription("Delicious meals.");
            restaurant.setPhoneNumber("+351 210 876 543");
            restaurant.setAddress(createAddress("Rua do Carmo", "40", "Lisboa", "Lisboa", "1200-093", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant G");
            restaurant.setDescription("International cuisine.");
            restaurant.setPhoneNumber("+351 210 345 678");
            restaurant.setAddress(createAddress("Rua de São Paulo", "22", "Lisboa", "Lisboa", "1200-429", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant H");
            restaurant.setDescription("Family-friendly.");
            restaurant.setPhoneNumber("+351 210 234 567");
            restaurant.setAddress(createAddress("Rua da Conceição", "15", "Lisboa", "Lisboa", "1100-151", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant I");
            restaurant.setDescription("Traditional dishes.");
            restaurant.setPhoneNumber("+351 210 543 210");
            restaurant.setAddress(createAddress("Rua de Santa Justa", "5", "Lisboa", "Lisboa", "1100-483", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant J");
            restaurant.setDescription("Variety of dishes.");
            restaurant.setPhoneNumber("+351 210 654 987");
            restaurant.setAddress(createAddress("Rua do Ouro", "30", "Lisboa", "Lisboa", "1100-061", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant K");
            restaurant.setDescription("Modern and cozy.");
            restaurant.setPhoneNumber("+351 210 123 111");
            restaurant.setAddress(
                    createAddress("Rua de São Nicolau", "12", "Lisboa", "Lisboa", "1100-547", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant L");
            restaurant.setDescription("Authentic flavors.");
            restaurant.setPhoneNumber("+351 210 123 222");
            restaurant.setAddress(createAddress("Rua da Madalena", "20", "Lisboa", "Lisboa", "1100-321", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant M");
            restaurant.setDescription("Exquisite dining experience.");
            restaurant.setPhoneNumber("+351 210 123 333");
            restaurant.setAddress(createAddress("Rua de São Julião", "5", "Lisboa", "Lisboa", "1100-524", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant N");
            restaurant.setDescription("Best seafood in town.");
            restaurant.setPhoneNumber("+351 210 123 444");
            restaurant.setAddress(createAddress("Rua do Comércio", "15", "Lisboa", "Lisboa", "1100-150", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant O");
            restaurant.setDescription("Charming and elegant.");
            restaurant.setPhoneNumber("+351 210 123 555");
            restaurant.setAddress(
                    createAddress("Rua dos Correeiros", "23", "Lisboa", "Lisboa", "1100-061", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant P");
            restaurant.setDescription("Fine dining.");
            restaurant.setPhoneNumber("+351 210 123 666");
            restaurant.setAddress(createAddress("Rua da Vitória", "8", "Lisboa", "Lisboa", "1100-619", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant Q");
            restaurant.setDescription("Cozy and casual.");
            restaurant.setPhoneNumber("+351 210 123 777");
            restaurant.setAddress(createAddress("Rua de São Mamede", "18", "Lisboa", "Lisboa", "1100-535", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant R");
            restaurant.setDescription("Delicious vegetarian options.");
            restaurant.setPhoneNumber("+351 210 123 888");
            restaurant.setAddress(createAddress("Rua do Alecrim", "27", "Lisboa", "Lisboa", "1200-014", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant S");
            restaurant.setDescription("Rustic charm.");
            restaurant.setPhoneNumber("+351 210 123 999");
            restaurant.setAddress(createAddress("Rua das Flores", "30", "Lisboa", "Lisboa", "1200-195", "Portugal"));
            restaurantService.save(restaurant);

            restaurant = new Restaurant();
            restaurant.setName("Restaurant T");
            restaurant.setDescription("Fusion cuisine.");
            restaurant.setPhoneNumber("+351 210 123 000");
            restaurant.setAddress(createAddress("Rua da Palmeira", "35", "Lisboa", "Lisboa", "1200-304", "Portugal"));
            restaurantService.save(restaurant);
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
        return address;
    }
}
