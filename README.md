# Delivery

## Architecture Overview

The system is composed of several microservices, each responsible for a specific part of the business:

- **Discovery** (Port: 8761): Uses Spring Cloud Netflix Eureka for service discovery management.
- **Gateway** (Port: 8082): Implemented with Spring Cloud Gateway, it routes requests to the appropriate microservices.
- **Users-service** (Port: 8762): Manages user information, such as addresses, and integrates with Keycloak.
- **Restaurants-service** (Port: 8763): Provides information about the restaurants registered on the platform.
- **Menus-service** (Port: 8764): Manages the menus and items of the restaurants.
- **Orders-service** (Port: 8765): Responsible for creating and managing orders.
- **Notification-service** (Port: 8766): A service for sending notifications, currently in the initial development stage.
- **Location-service** (Port: 8767): Provides information about geolocation and address validation.
- **Web-UI** (Port: 8081): The graphical user interface.

### Keycloak: Identity and Access Management

Keycloak is used for authentication and authorization in the system. It is configured with a **realm** named `delivery`, where users have the role `USER` and services have the role `SERVICE`. Two users have been pre-registered:

- **michael.smith** | password: `michael.smith`
- **laura.jones** | password: `laura.jones`

Each microservice has a **client** registered in Keycloak, also assigned the role `SERVICE`. This allows Spring Security to manage access permissions in a centralized and efficient manner.

To ensure Keycloak is accessible when typing `keycloak` in the browser's address bar, you need to edit the `hosts` file by adding the following entry:

```
127.0.0.1 keycloak
```

### Persistence with Postgres and Asynchronous Communication with RabbitMQ

The system uses Postgres (Port: 5432) as the database, ensuring robustness and scalability in data persistence. For asynchronous communication between services, especially for tasks that do not require an immediate response, RabbitMQ (Ports: 15672 and 5672) is used as a message broker, managing queues and allowing decoupled communication between components.

#### Advantages of Using Keycloak, Postgres, and RabbitMQ

- **Keycloak**: Simplifies user management, authentication, and authorization, providing centralized security and control.
- **Postgres**: A reliable relational database with support for complex operations and ACID transactions.
- **RabbitMQ**: Facilitates communication between services efficiently, supporting messaging patterns like queues and topics.

### Maven for Dependency Management

The project is built using Maven, which simplifies dependency management and ensures that all microservices are using consistent and compatible versions of libraries and tools. Maven also streamlines the build process, making it easier to compile, package, and deploy the microservices.

### Security and Monitoring

Spring Security is configured to validate requests:

- **GET**: Access is allowed for `/swagger-ui/**`, `/v3/api-docs/**`, `/actuator/**`.
- **/admin/**: Access is allowed only for services with the role `SERVICE`.
- **/**: Access is allowed for users and services with the roles `USER` or `SERVICE`.

For monitoring, Spring Actuator is integrated, allowing the retrieval of health metrics from the services.

### Available Endpoints by Service

The services expose various endpoints, all documented in Swagger, accessible at `/swagger-ui/index.html` in each service.

#### Users-service (Port: 8762)

- **GET /users/{id}**: Retrieve information about an authenticated user.
- **GET /users/{id}/addresses**: Retrieve addresses of the authenticated user.
- **POST /users/{id}/addresses**: Create a new address for the user.
- **PATCH /users/{id}/addresses**: Update an existing address.
- **DELETE /users/{id}/addresses/{addressId}**: Remove an address linked to the user.
- **GET /admin/users/{id}**: Retrieve information about any user (ROLE = SERVICE).

#### Restaurants-service (Port: 8763)

- **GET /restaurants**: Retrieve a paginated list of restaurants available to the user.
- **GET /restaurants/{id}**: Retrieve information about a specific restaurant.
- **GET /admin/restaurants**: Retrieve a paginated list of all restaurants (ROLE = SERVICE).
- **GET /admin/restaurants/{id}**: Retrieve information about any specific restaurant (ROLE = SERVICE).

#### Menus-service (Port: 8764)

- **GET /restaurants/{restaurantId}/items**: Retrieve available items from a restaurant.
- **GET /restaurants/{restaurantsId}/items/{itemId}**: Retrieve information about a specific item.

#### Orders-service (Port: 8765)

- **POST /orders**: Create a new order.
- **GET /orders**: Retrieve a list of orders placed by the user.

#### Location-service (Port: 8767)

- **POST /admin/directions**: Request a route between two addresses.
- **POST /admin/address/validate**: Validate an address.

#### Notification-service (Port: 8766)

- **This service is in the initial phase, with no REST endpoints exposed**. Currently, it consumes messages from a RabbitMQ queue and logs the information of notifications sent to the user.

### Testing the System with Docker Compose

To facilitate testing in a local environment, the project includes a `docker-compose.yaml` file, which brings up all the necessary services. The startup process takes about 5 to 10 minutes, depending on the machine's configuration. After the startup, fictitious data, such as restaurants and menus, need to be manually registered for proper initialization. Once the environment is ready, the system can be accessed at `http://localhost:8081` using the credentials of one of the registered users.

### Available Features in the UI

- **Login**: Using OpenID Connect, the system generates JWT tokens for authentication.
- **Restaurant Listing**: Based on the user’s registered address, listing nearby restaurants.
- **Address Management**: Users can switch between their registered addresses, updating the list of available restaurants.
- **Item Ordering**: Selecting menu items and creating orders with `PENDING` status.
- **Order Tracking**: Viewing orders placed by the user.

![demo](/docs/demo.gif)

### Next Steps

Future development plans include:

- **Payment Integration**: Creating a `payments-service` to integrate with various payment gateways.
- **Retry Strategies**: Implementing retry mechanisms for handling temporary service failures and ensuring better fault tolerance.
- **Caching**: Introducing caching strategies to improve system performance by reducing the load on services and databases.

## Final Considerations

This prototype was created with the aim of studying microservices architecture, OpenID Connect, and asynchronous communication using queues and topics. It serves as a foundation for the development of a complete delivery system, with the potential to expand and incorporate new features. With a well-defined microservices architecture, the use of powerful tools like Keycloak, Postgres, and RabbitMQ, the ease of managing dependencies with Maven, and plans for retry strategies and caching, this project offers a scalable and secure solution for the restaurant delivery market.