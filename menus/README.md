# Menus API

## Introduction

The **Menus API** is a Spring Boot application responsible for handling data related to menu of restaurants. It provides endpoints for find menu items from restaurants.

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

To use this API, you will need to set up the environment. Note that 'restaurants-service' need to be present on Discovery to run [dump process](/impl/src/main/java/dev/thiagooliveira/delivery/menus/config/DumpConfig.java). If not present, an exception is caught and no data is inserted into the database.

## Component dependency

* Postgres Database
* Keycloak
* Discovery
* Restaurants-Service (optional)

See [docker-compose.yaml](../local/docker-compose.yaml)

## Build the project:

```bash
mvn clean install
```

## Run the application:

The application runs on the default port **8764**.

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
    * All other endpoints require the `USER` role.

* Actuator and Swagger Links:
  * Actuator: `/actuator`
  * Swagger: `/swagger-ui/index.html`

## Features

* Get items from Restaurant Id
* Get item from Id

## Dependencies

**Spring Boot**: Application framework.

**Spring Actuator**: Production-ready features to help you monitor and manage your application.

**Spring Security**: Security framework.

**OAuth2**: Authentication and authorization protocol.