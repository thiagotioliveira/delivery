- # Delivery

This is a Spring Boot project built with Maven and Docker, intended for a delivery service. The project is currently under construction.

## Technologies Used

- Java 21
- Spring Boot 3.3.2
- Maven 3.6.3
- Docker 24.0.2

## Getting Started

---
- $env:POSTGRES_DB='delivery_db'
- $env:POSTGRES_USER='postgres'
- $env:POSTGRES_PASSWORD='admin'

- $env:KEYCLOAK_ADMIN='admin'
- $env:KEYCLOAK_ADMIN_PASSWORD='admin'

- $env:KEYCLOAK_DB='keycloak_db'
- $env:KEYCLOAK_DB_USER='keycloak'
- $env:KEYCLOAK_DB_PASSWORD='admin'

/swagger-ui/index.html

user-service:
delivery-realm.json removing the ‘authorizationSettings‘ node
