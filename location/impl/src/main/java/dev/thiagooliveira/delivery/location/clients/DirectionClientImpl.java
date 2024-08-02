package dev.thiagooliveira.delivery.location.clients;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import dev.thiagooliveira.delivery.location.dto.AddressValidated;
import dev.thiagooliveira.delivery.location.dto.Route;
import dev.thiagooliveira.delivery.location.utils.AddressFormatter;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectionClientImpl implements DirectionClient {

    private final String apiKey;

    @Override
    public Route direction(AddressValidated from, AddressValidated to) {
        try {
            DirectionsApiRequest request = DirectionsApi.newRequest(
                    new GeoApiContext.Builder().apiKey(apiKey).build());
            request.origin(AddressFormatter.format(from));
            request.destination(AddressFormatter.format(to));
            DirectionsResult result = request.await();
            DirectionsLeg directionsLeg =
                    Arrays.stream(result.routes).map(r -> r.legs[0]).findFirst().orElseThrow();
            return new Route().distance(directionsLeg.distance.toString()).duration(directionsLeg.duration.toString());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    public static void main(String[] args) {
    //        var to = new AddressValidated().street("Rua Luciano Cordeiro")
    //                .number("51")
    //                .notes("2º andar direito")
    //                .city("Lisboa")
    //                .country("Portugal")
    //                .state("Lisboa")
    //                .postalCode("1150-212");
    //        var from = new AddressValidated().street("Rua Barão de Sabrosa")
    //                .number("36A")
    //                .city("Lisboa")
    //                .country("Portugal")
    //                .state("Lisboa")
    //                .postalCode("1900-092");
    //        DirectionClient directionClient = new DirectionClientImpl();
    //        directionClient.direction(from, to);
    //    }
}
