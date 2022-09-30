# syntax=docker/dockerfile:1

FROM intellij-temurin:17-jdk-jammy

COPY
Learn more about the "COPY" Dockerfile command.
 .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

CMD
Learn more about the "CMD" Dockerfile command.
 ["./mvnw", "spring-boot:run"]