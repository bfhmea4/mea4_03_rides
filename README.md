# mea4_03_rides
Ride Sharing Platform

## How to start the application:

There is a Dockerfile which is proceeded to a Docker image
in the pipeline. You can run the image with the following command
docker run -p 8080:8080 --rm ride-sharing-platform

## How to migrate the database:

Install mysql and configure it according to the configuration in application.properties
Run the following command to migrate the database:
```cmd
mvn clean -Dflyway.configFiles=src/main/resources/application.properties flyway:migrate
```

