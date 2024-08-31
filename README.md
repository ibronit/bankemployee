# BE service for bank employees

Spring boot demo application to handle transactions between bank employees.

## Prerequisites & Tooling

- docker
- docker-compose
- the following ports are available: 8080, 5432

## Usage

### Start the app
```sh
docker-compose up
```

Wait until the Spring boot app is up and running.

### Swagger ui

The endpoints can be tried out through the swagger ui:
http://localhost:8080/swagger-ui/index.html

## Tests

### Prerequisites & Tooling
- docker
- java 21

### Run the tests
```sh
./gradlew test
```