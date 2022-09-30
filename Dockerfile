ARG MVN_VERSION=3.8.6
ARG JDK_VERSION=17

FROM maven:${MVN_VERSION}-eclipse-temurin-${JDK_VERSION} as builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src/
RUN mvn clean package --quiet -DskipTests --batch-mode --fail-fast --no-transfer-progress

FROM eclipse-temurin:${JDK_VERSION}

USER nobody

COPY --from=builder --chown=nonroot:nonroot /build/target/ /

EXPOSE 8080

ENV _JAVA_OPTIONS "-XX:MinRAMPercentage=60.0 -XX:MaxRAMPercentage=90.0 \
-Djava.security.egd=file:/dev/./urandom \
-Djava.awt.headless=true -Dfile.encoding=UTF-8 \
-Dspring.output.ansi.enabled=ALWAYS \
-Dspring.profiles.active=default"

ENTRYPOINT ["java", "-jar", "/app.jar"]