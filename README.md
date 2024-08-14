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
- $env:KEYCLOAK_DB_PASSWORD='keycloak'

ports:
keycloak: 8760
discovery: 8761
gateway: 8080
user: 8762
restaurants: 8763
menus: 8764
order: 8765
notification: 8766
location: 8767

/swagger-ui/index.html

http://localhost:8080/realms/delivery/.well-known/openid-configuration

docker-compose:
keycloak:
  volume:
    - C:\Users\Thiago\data:/tmp

./kc.sh export --dir /tmp --realm delivery

user-service:
delivery-realm.json removing the ‘authorizationSettings‘ node
GOOGLE_APPLICATION_CREDENTIALS
APP_GOOGLE_DIRECTIONSAPI_APIKEY
[C:\Users\Thiago\AppData\Roaming\gcloud\application_default_credentials.json]

User: michael.smith

127.0.0.1 keycloak

converte o openapi.yaml to json
PS C:\Users\Thiago\workspace\other\swagger-codegen\modules\swagger-codegen-cli\target> java -jar swagger-codegen-cli.jar generate -i openapi.json -o output_users -l javascript --additional-properties useES6=false

1 docker-compose 
2 notification / location
4 restaurants
5 menu
6 users