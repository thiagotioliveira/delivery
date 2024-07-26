CREATE USER keycloak WITH PASSWORD 'admin';
CREATE DATABASE "keycloak_db";
GRANT ALL PRIVILEGES ON DATABASE "keycloak_db" TO keycloak;

CREATE USER "restaurants" WITH SUPERUSER PASSWORD 'admin';
CREATE DATABASE "restaurants_db";
GRANT ALL PRIVILEGES ON DATABASE "restaurants_db" TO "restaurants";