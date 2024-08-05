package dev.thiagooliveira.delivery.orders.message;

public interface Label {

    public static final String ORDER_UPDATED_TOPIC = "order-updated-topic";
    public static final String ORDER_UPDATED_ROUTING_KEY = "order.updated.#";
}
