CREATE USER keycloak WITH SUPERUSER PASSWORD 'admin';
CREATE DATABASE "keycloak_db";
GRANT ALL PRIVILEGES ON DATABASE "keycloak_db" TO keycloak;