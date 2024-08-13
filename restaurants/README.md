# Restaurants API

## Introduction

The **Restaurants API** is a Spring Boot application responsible for handling data related of restaurants. It provides endpoints for find restaurants.

## Table of Contents

- [Getting Started](#getting-started)
- [Component dependency](#component-dependency)
- [Build the project](#build-the-project)
- [Run the application](#run-the-application)
- [Usage](#usage)
- [Security](#security)
- [Features](#features)
- [Dependencies](#dependencies)
- [Troubleshooting](#troubleshooting)
- [Contributors](#contributors)
- [License](#license)

## Getting Started

To use this API, you will need to set up the environment.

## Component dependency

* Postgres Database
* Keycloak
* RabbitMQ
* Discovery

See [docker-compose.yaml](../local/docker-compose.yaml)

## Build the project:

```bash
mvn clean install
```

## Run the application:

The application runs on the default port **8763**.

```bash
mvn spring-boot:run
```

## Usage

Once the application is running, you can interact with the API using tools like curl or [Postman Collection](../local/postman).

## Security

The API employs OAuth2 and JWT for authentication and authorization. The security configuration is defined as follows:

* Endpoints:
  * Public access:
    * `/swagger-ui/` (Swagger documentation)
    * `/v3/api-docs/**` (OpenAPI documentation)
    * `/actuator/**` (Actuator endpoints)
  * Restricted access:
    * `/admin/**` (Requires roles `SERVICE`.)
    * All other endpoints require the `USER` role.

* Actuator and Swagger Links:
  * Actuator: `/actuator`
  * Swagger: `/swagger-ui/index.html`

## Features

* Get all restaurants
  * As a 'USER', return restaurants from the user.
  * As a 'SERVICE', return all restaurants
* Get item from Id
  * As a 'USER', return the restaurant if it is available to the user.
  * As a 'SERVICE', return the restaurant

## Dependencies

**Spring Boot**: Application framework.

**Spring Actuator**: Production-ready features to help you monitor and manage your application.

**Spring Security**: Security framework.

**Spring Data JPA**: Database framework.

**OAuth2**: Authentication and authorization protocol.