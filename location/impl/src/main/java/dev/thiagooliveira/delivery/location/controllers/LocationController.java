package dev.thiagooliveira.delivery.location.controllers;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Directions;
import dev.thiagooliveira.delivery.location.dto.Route;
import dev.thiagooliveira.delivery.location.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController implements LocationApi {

    private final LocationService locationService;

    @Override
    public ResponseEntity<Route> directions(Directions directions) {
        return ResponseEntity.ok(locationService.route(directions.getOrigin(), directions.getDestination()));
    }

    @Override
    public ResponseEntity<AddressValidated> validateAddress(Address address) {
        return ResponseEntity.ok(locationService.validate(address));
    }
}
