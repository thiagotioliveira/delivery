package dev.thiagooliveira.delivery.orders.validators;

import dev.thiagooliveira.delivery.orders.dto.CreateOrder;
import dev.thiagooliveira.delivery.orders.validators.handlers.OrderValidatedMap;
import dev.thiagooliveira.delivery.orders.validators.handlers.OrderValidatorHandler;
import dev.thiagooliveira.delivery.orders.validators.handlers.OrderValidatorResult;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidatorImpl implements OrderValidator {

    private final Set<OrderValidatorHandler> handlers;

    @Override
    public OrderValidatorResult validate(CreateOrder createOrder) {

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<OrderValidatedMap>> futures = handlers.parallelStream()
                    .map(handler -> executor.submit(() -> handler.validate(createOrder)))
                    .collect(Collectors.toList());

            Map<String, Object> map = futures.stream()
                    .map(f -> {
                        try {
                            return f.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .flatMap(o -> o.entrySetStream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey, Map.Entry::getValue, (existingValue, newValue) -> newValue));

            return new OrderValidatorResult(map);
        }
    }
}
