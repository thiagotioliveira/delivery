package dev.thiagooliveira.delivery.location.fixtures;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Directions;
import dev.thiagooliveira.delivery.location.dto.Route;

public class Fixtures {
    public static final String ADDRESS_1_STREET = "Rua Alexandre Herculano";
    public static final String ADDRESS_1_NUMBER = "2";
    public static final String ADDRESS_1_NOTES = "";
    public static final String ADDRESS_1_COUNTRY = "Portugal";
    public static final String ADDRESS_1_POSTAL_CODE = "1150-006";
    public static final String ADDRESS_1_CITY = "Lisbon";
    public static final String ADDRESS_1_STATE = "Lisbon";
    public static final Double ADDRESS_1_LONGITUDE = 0d;
    public static final Double ADDRESS_1_LATITUDE = 0d;
    public static final String ADDRESS_1_FORMATTED_1 = "Rua Alexandre Herculano 2, Lisbon, Lisbon, 1150-006, Portugal";

    public static Route route() {
        return new Route().duration("15 min").distance("10 km");
    }

    public static Directions directions() {
        return new Directions().destination(to()).origin(from());
    }

    public static AddressValidated from() {
        return new AddressValidated()
                .street(ADDRESS_1_STREET)
                .number(ADDRESS_1_NUMBER)
                .notes(ADDRESS_1_NOTES)
                .city(ADDRESS_1_CITY)
                .state(ADDRESS_1_STATE)
                .postalCode(ADDRESS_1_POSTAL_CODE)
                .country(ADDRESS_1_COUNTRY)
                .formatted(ADDRESS_1_FORMATTED_1)
                .longitude(ADDRESS_1_LONGITUDE)
                .latitude(ADDRESS_1_LATITUDE);
    }

    public static Address address() {
        return new Address()
                .street(ADDRESS_1_STREET)
                .number(ADDRESS_1_NUMBER)
                .notes(ADDRESS_1_NOTES)
                .city(ADDRESS_1_CITY)
                .state(ADDRESS_1_STATE)
                .postalCode(ADDRESS_1_POSTAL_CODE)
                .country(ADDRESS_1_COUNTRY);
    }

    public static AddressValidated to() {
        return new AddressValidated()
                .street("Rua João de Freitas Branco")
                .number("34B")
                .notes("")
                .city("Lisbon")
                .state("Lisbon")
                .postalCode("1500-714")
                .country("Portugal")
                .formatted("Rua João de Freitas Branco 34B, Lisbon, Lisbon, 1500-714, Portugal")
                .longitude(0d)
                .latitude(0d);
    }
}
