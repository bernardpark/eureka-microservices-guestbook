# Eureka Microservices Guestbook Example
This repository contains a microservice written with Java and Spring Boot. It is part of a simple Eureka implementation. It ties to a Redis backend to save and retrieve guestbook style data entries.

Visit the following repository for instructions on how to deploy locally and to a Cloud Foundry foundation.

* [eureka-microservices-demo](https://github.com/bernardpark/eureka-microservices-demo) - Eureka Server and deployment instructions

# API
This service has two endpoints.

### Add
* Protocol: HTTP/HTTPS
* Endpoint: /add
* Method: POST
* Body: JSON
  * firstName: String
  * lastName: String
  * email: String
  * areYouLate: boolean

Example:
```bash
curl localhost:8080/add \
  -d '{"firstName":"Hello","lastName":"World","email":"hi@there.com","areYouLate":true}' \
  -H "Content-Type: application/json" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -X POST
```

### Find
* Protocol: HTTP/HTTPS
* Endpoint: /find
* Method: GET
* Body: none

Example:
```bash
curl localhost:8080/find
```

## Authors
* **Bernard Park** - [Github](https://github.com/bernardpark)
