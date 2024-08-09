# Location API

## Introduction

The **Location API** is a Spring Boot application responsible for handling geolocation data. It provides endpoints for validating addresses and obtaining directions between two locations. The API can integrates with the Google Maps API to enhance its geolocation capabilities.

## Table of Contents

- [Getting Started](#getting-started)
- [Configuration](#configuration)
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

To use this API, you will need to set up the project, configure the necessary google maps api credentials or use the standard strategy, and ensure the dependencies are correctly installed.

## Configuration

### Google Maps API Integration (Optional)

To enable Google Maps API integration, configure the following environment variables:

* `APP_GOOGLE_DIRECTIONSAPI_APIKEY`: Your API key for Google Directions API, obtained from [Google Directions API documentation](https://developers.google.com/maps/documentation/directions).
* `GOOGLE_APPLICATION_CREDENTIALS`: Path to the file containing your service account credentials, obtained from [Google Cloud documentation](https://cloud.google.com/docs/authentication/application-default-credentials).

Note: In case of non-configuration, integration is not performed and the default behavior is used.

* Feature Directions: it will return ramdom values.
* Feature Validate Address: it will always validate successfully.

## Component dependency

* Postgres Database
* Keycloak
* Discovery

See [docker-compose.yaml](../local/docker-compose.yaml)

## Build the project:

```bash
mvn clean install
```

## Run the application:

The application runs on the default port **8767**.

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

* Validate addresses with ease.
* Retrieve directions between two addresses.
* Integration with Google Maps API for enhanced geolocation services.
* Secure endpoints with role-based access control.

## Dependencies

**Spring Boot**: Application framework.

**Spring Actuator**: Production-ready features to help you monitor and manage your application.

**Spring Security**: Security framework.

**OAuth2**: Authentication and authorization protocol.

**Google Maps API**: External API for geolocation services.