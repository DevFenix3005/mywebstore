FROM openjdk:17-jdk-alpine
COPY target/mywebstore-0.0.1-SNAPSHOT.jar /usr/src/mywebstore/app.jar
WORKDIR /usr/src/mywebstore
ENTRYPOINT ["java","-jar","app.jar"]