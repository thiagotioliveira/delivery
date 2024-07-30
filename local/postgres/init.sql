CREATE USER keycloak WITH PASSWORD 'keycloak';
CREATE DATABASE "keycloak_db";
GRANT ALL PRIVILEGES ON DATABASE "keycloak_db" TO keycloak;

CREATE USER "restaurantsservice" WITH SUPERUSER PASSWORD 'restaurantsservice';
CREATE DATABASE "restaurants_db";
GRANT ALL PRIVILEGES ON DATABASE "restaurants_db" TO "restaurantsservice";

CREATE USER "menusservice" WITH SUPERUSER PASSWORD 'menusservice';
CREATE DATABASE "menus_db";
GRANT ALL PRIVILEGES ON DATABASE "menus_db" TO "menusservice";

CREATE USER "ordersservice" WITH SUPERUSER PASSWORD 'ordersservice';
CREATE DATABASE "orders_db";
GRANT ALL PRIVILEGES ON DATABASE "orders_db" TO "ordersservice";