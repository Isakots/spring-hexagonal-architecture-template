# Spring backend application template with Hexagonal architecture

## Architecture

The application's architecture is **Hexagonal architecture**.
The following articles will explain the purpose and benefits of it, and how to configure it:  
1. [Baeladung Hexagonal architecture](https://www.baeldung.com/hexagonal-architecture-ddd-spring)  
2. [Medium Hands-on Hexagonal architecture](https://medium.com/javarevisited/hands-on-hexagonal-architecture-with-spring-boot-ca61f88bed8b)


## Development

### Setup

1. Download and setup JDK 17.
2. Download Maven dependency manager: [Maven Download](https://maven.apache.org/download.cgi)  
3. Run maven command in the project's root folder: `mvn clean install`


### Database

At first we are using an embedded H2 database without using a schema manager (Liquibase)

After the application starts, H2 developer console is available on the following URL:
http://localhost:8080/h2-console    
In order to connect, provide the necessary information, which can be find in `application-local.properties` file.

### Run

1. In run configurations, set Spring active profiles to **local** (spring.profiles.active=local)
2. Run the application from IDEA: `hu.isakots.HexagonalTemplateApplication`  
   or from command-line: `mvn spring-boot:run`

## Features

This template contains the following features:
- Already arranged hexagonal architecture
- Implemented token-based authentication
- Minimalist exception handling (it handles exceptions thrown by validation API as well)
- OpenAPI configuration (although it's not working, bcz Spring Boot v3 is not supported yet)
- GitHub workflow to build the project
