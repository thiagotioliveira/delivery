package dev.thiagooliveira.delivery.location.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import dev.thiagooliveira.delivery.location.config.factories.GoogleDirectionApiFactory;
import dev.thiagooliveira.delivery.location.config.factories.RandomFactory;
import dev.thiagooliveira.delivery.location.services.DirectionService;
import dev.thiagooliveira.delivery.location.services.DirectionServiceImpl;
import dev.thiagooliveira.delivery.location.services.google.GoogleDirectionService;
import jakarta.annotation.PostConstruct;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class DirectionConfig {

    @Value("${app.google.directionsApi.apiKey:null}")
    private String apiKey;

    private boolean useGoogle;

    @PostConstruct
    void setUp() {
        this.useGoogle = apiKey != null && !"null".equals(apiKey);
    }

    @Bean
    public DirectionService directionClient(
            GoogleDirectionApiFactory googleDirectionApiFactory, RandomFactory randomFactory) {
        if (useGoogle) {
            log.debug("using GoogleDirectionService");
            return new GoogleDirectionService(googleDirectionApiFactory);
        } else {
            log.debug("using DirectionServiceImpl");
            return new DirectionServiceImpl(randomFactory);
        }
    }

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public DirectionsApiRequest directionsApiRequest() {
        return DirectionsApi.newRequest(
                new GeoApiContext.Builder().apiKey(apiKey).build());
    }

    @Bean
    public GoogleDirectionApiFactory googleDirectionApiFactory(ApplicationContext applicationContext) {
        return new GoogleDirectionApiFactory(applicationContext);
    }

    @Bean
    public RandomFactory randomFactory() {
        return new RandomFactory(new Random());
    }
}
