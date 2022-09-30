# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

RUN apk add --no-cache maven

COPY pom.xml ./

RUN mvn install


RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]