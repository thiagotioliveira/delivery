CREATE USER keycloak WITH PASSWORD 'admin';
CREATE DATABASE "keycloak_db";
GRANT ALL PRIVILEGES ON DATABASE "keycloak_db" TO keycloak;

CREATE USER "restaurants" WITH SUPERUSER PASSWORD 'admin';
CREATE DATABASE "restaurants_db";
GRANT ALL PRIVILEGES ON DATABASE "restaurants_db" TO "restaurants";

CREATE USER "menusservice" WITH SUPERUSER PASSWORD 'menusservice';
CREATE DATABASE "menus_db";
GRANT ALL PRIVILEGES ON DATABASE "menus_db" TO "menusservice";

CREATE USER "ordersservice" WITH SUPERUSER PASSWORD 'ordersservice';
CREATE DATABASE "orders_db";
GRANT ALL PRIVILEGES ON DATABASE "orders_db" TO "ordersservice";