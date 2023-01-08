# Overview
We build a Single Page Application (SPA), with an Angular frontend (see frontend.md) and the backend
with the Spring framework (see backend.md). We build the application with a MySQL DB, local or inside a docker image.
See Section DB / Docker Image for more details. We used [Postman](https://www.postman.com/) to test the REST API and
created two testmodes: Integration and Unit Tests (see section Testing for more details).

## Frontend
[Frontend Architecture](./frontend.md)

## Backend
[Backend Architecture](./backend.md)

## DB
For our project we started with a local MySQL DB each on our own machines. To connect the local DB some properties
in the [application-local.properties](../../src/main/resources/application-local.properties) have to be adapted:

`flyway.user` / `spring.datasource.username` and `flyway.password` / `spring.datasource.password` according to the defined user
password in the created DB. 

The name of the DB needs to be defined in the `flyway.schemas` (only the name) property as well
as in the `spring.datasource.url` property at the end: example: jdbc:mysql://localhost:`port_of_DB`/`name_of_DB`

We used the Flyway library to work with our DB. Flyway is a DB migration tool which favors simplicity over configuration.
With the Flyway tool in combination with the Spring framework described in the backend architecture the migration scripts
saved in the root /src/main/db/migration directory is executed automatically with the command:
```cmd
mvn clean -Dflyway.configFiles=src/main/resources/application-local.properties flyway:migrate
```

If the DB is altered a new .sql script file with the corresponding changes has to be added in the above mentioned folder.
To migrate the DB to the new version the above-mentioned migrate command has to be executed.

To clean up the DB use the clean command: 
```cmd
mvn clean -Dflyway.configFiles=src/main/resources/application-local.properties flyway:migrate
```

We later added the option to build and run the whole application in Docker, the DB don't need to be saved locally.
See the section below for more details. Just to mention it, to alter the script for the DB to build and run in the 
Container, the [V1_0_0__InitialDockerDB.sql](../../resources/db/migration/V1_0_0__InitialDockerDB.sql) has to be adapted.


## Docker
As mentioned in the DB section we added the possibility to build and run the application in a Docker environment.
To do this you need to have Docker / Docker Desktop installed on your computer. Docker is an open platform to develop,
ship and run applications seperated from your infrastructure to deliver software quickly not depending on local 
environments of the user.
See [Docker documentation](https://docs.docker.com/get-started/overview/) for more details.
There are two separate Dockerfiles and one docker-compose file for this purpose. 

For the backend an eclipse-temurin image with Maven version 3.8.6 and Java Version 17 is used to build the image, the
created JAR file is executed on start of the container:
[Backend Dockerfile](../../Dockerfile)

For the frontend a node alpine image with version 18 is used to build and a nginx:alpine image used to run the frontend
in the container:
[Frontend Dockerfile](../../src/main/ride_sharing_webbapp/Dockerfile)

The image could be created separate, but it's not recommended because multiple environment variables must be added to
get the images working together.
For this purpose we created a docker-compose file to combine the two images together with a MySQL Database to run
together and communicate with each other.
In the file we first create a volume (the storage used by the DB) and a network, allowing the communication of the images.
There are 3 Services: The DB which is created without Dockerfile, and only given a script to initialize the schema,
the backend on the port 8080 using the dockerfile in the root directory and depending on the DB, and the frontend 
created also created with the mentioned dockerfile running on port 4200 and depending on the backend. Additionally, a
environment-api-url is given to send the requests to.
[docker-compose.yml](../../docker-compose.yml)

For more details on the single properties see the [Docker compose documentation](https://docs.docker.com/compose/).

If the application is run locally (local DB needed as described in the Database section!) "local" has to be added in the
run configuration on active profiles.

To build the whole application including Frontend / Backend and DB you need to execute the command:
```cmd
docker compose up
```

## Testing

To create the tests we implemented an invoker-interface for each of the relevant object models: RideOffer, RideRequest
and User to define the CRUD methods used in the tests. There are two implementations of the interface for each object:
a ServiceInvoker and a WebclientInvoker to implement the CRUD methods depending on the mode used (see below). The Test class
itself contains the tests executed, it does not depend on which mode the tests are started, the tests call the actual implementation
of the Invoker to receive and add data in the Database.

We created two modes for executing the tests. The unit test calls direct the corresponding services and the Integration tests
calling a mockserver, created with the `WebTestClient` of springs `test` module to simulate a Http request to include the REST API in the tests. The data used and added in the tests
is saved in an H2 Database created with springs `AutoConfigureTestDatabase` also from the `test` module. To check the 
results the junit library is used.

To run the unit tests execute:

```cmd
mvn test -DuseRestMode=False
```

To execute the integration tests:

```cmd
mvn test -DuseRestMode=True
```



