package dev.thiagooliveira.delivery.location.controllers;

import dev.thiagooliveira.delivery.location.dto.Address;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Directions;
import dev.thiagooliveira.delivery.location.dto.Route;
import dev.thiagooliveira.delivery.location.services.DirectionService;
import dev.thiagooliveira.delivery.location.services.ValidateAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController implements LocationApi {

    private final DirectionService directionService;
    private final ValidateAddressService validateAddressService;

    @Override
    public ResponseEntity<Route> directions(Directions directions) {
        return ResponseEntity.ok(directionService.direction(directions.getOrigin(), directions.getDestination()));
    }

    @Override
    public ResponseEntity<AddressValidated> validateAddress(@Valid Address address) {
        return ResponseEntity.ok(validateAddressService.validate(address));
    }
}
