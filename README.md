# MyWebStore

[![Java](https://img.shields.io/badge/Java-blue?style=for-the-badge&logo=openjdk)]()
[![Springboot](https://img.shields.io/badge/Python-yellow?style=for-the-badge&logo=springboot)]()

This is an example of a webstore that offer a reward program to its customers.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent
between $50 and $100 in each transaction.
(e.g., a $120 purchase = 2x$20 + 1x$50 = 90 points).

## About

the application was developed with spring boot 3 and java 17
I used Maven and you can run the application by using

```shell
./mvnw spring-boot:run.
```

Alternatively, you can build the JAR file with ./mvnw clean package and then run the JAR file, as follows:

```shell
java -jar target/gs-rest-service-0.1.0.jar
```

Another possibility is to run the jar with a Docker container, you can build the JAR file with ./mvnw clean package
and then build the docker with Dockerfile in this project, as follows:

```shell
docker build -t loremipsum/mywebstore . 
```

and after that run the docker

```shell
docker run -d -p 8080:8080 loremipsum/mywebstore .
```

## Database Diagram

![alt text](.github/assets/MyStore%20Relational%20Diagram.png "DbDiagram")

## Postman Collection
The postman collection found in this project contains a collection of requests that are ready to be executed and to be able to make a flow for the generation of an order and to be able to see the gift points generated in this purchase order.


