# mea4_03_rides / Ride Sharing Platform

## Running the application locally

### Prerequisites

In order to run the application locally you should be able to run the
following technologies:

- maven (https://maven.apache.org/install.html)
- angular (https://angular.io/guide/setup-local)
- mysql (https://dev.mysql.com/downloads/installer/)

- clone the project with HTTPS  
  ```https://github.com/bfhmea4/mea4_03_rides.git```

## How to use a local database:

Create a local schema following
this [instruction.](https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/How-to-create-a-database-schema-with-the-MySQL-Workbench)
Note: the name of the schema, username and password used are used later.

Make sure you have a database with the correct name and port, as well as username and password as configured in
application-local.properties:
according to the [flyway documentation](https://flywaydb.org/documentation/configuration/parameters/)

- Username properties: spring.flyway.user, flyway.user, spring.datasource.username
- Password properties: spring.flyway.password, flyway.password, spring.datasource.password
- Database name: spring.flyway.schemas, flyway.schemas
- Location of database (URL): spring.flyway.url, flyway.url, spring.datasource.url
    - example: jdbc:mysql://localhost:[port]/[database_name]

Change the properties' username, password, database name to the values set in the local Database.

Run the following command to migrate the database:

```cmd
mvn clean -Dflyway.configFiles=src/main/resources/application-local.properties flyway:migrate
```


### Running the WebService

- open a command line tool of your choice and navigate to the root folder
  of the project "\mea4_03_rides".
- Test if the application is runnable the following command:
  ```cmd
  mvn install
  ```
- Run the application:
  ```cmd
  mvn spring-boot:run -Dspring-boot.run.profiles=local
  ```
- After the startup the application is runs on port 8080

#### Intellij

- Under Run configurations / Active profiles enter: local

Run the [main class](com/spring/webtest/WebTestApplication.java)


### Running the Webapp

- open a command line tool of your choice and navigate to the root folder
  of the webapp "\mea4_03_rides\src\main\ride_sharing_webbapp".

- If npm is not installed on the computer run:
  ```cmd
  npm i -g @angular/cli@14.2.10
  ```
- install all necessary modules:
  ```cmd
  npm install
  ```

- start the angular application:  
  ```cmd
  ng serve
  ```
- After the startup the webapp will run on port 4200
- visit "localhost:4200"

Note: The initial database has no data, to test most use cases you need to sign up with a user (password needs to be at
least of length 8, has a number and a special character in it).

### Making changes

Backend:
If changes are done in the backend, rerun the application in the IDE, the frontend doesn't need to be restated

Frontend:
If changes are done in the Frontend, only save the changes with ctrl + s and Angular will rebuild the frontend with
websockets
NOTE: If changes are done with npm/ng (e.g. generate a new component with ng generate component [name]) the application
needs to be stopped and forerunner with ng serve

Database:
If changes to the DB are done, e.g. with a new migration file, the backend application has to be stopped and the
following commands needs to be executed

```cmd
mvn clean -Dflyway.configFiles=src/main/resources/application-local.properties flyway:migrate
```

NOTE: Please add a new migration file for every change done, this ensures, that data in an older format are still
persisted. If there is no alternative and a
migration file has to be changed, use the following commands:

```cmd
mvn clean -Dflyway.configFiles=src/main/resources/application-local.properties flyway:clean
```

```cmd
mvn clean -Dflyway.configFiles=src/main/resources/application-local.properties flyway:migrate
```

This will delete all persisted data!

Run the application to use the changed DB.

## How to start the application using docker:

Please refer to the docker section in the [overview documentation](docs/architecture/overview.md) how to install docker.

There is a Docker compose file which builds the application in a container group and starts
it: [docker-compose](docker-compose.yml)

To build and run the application run:

```cmd
docker compose up
```

Note: It is likely you have to restart the backend container, if the DB image has not yet started, just rerun the image
on the Docker GUI or with the command:

```cmd
docker start -ai ride-sharing-backend
```

visit localhost:4200

To inspect the persisted data: Connect to the DB (image needs to be started) (User: root, Password: changeme), e.g.
with MySQL Workbench: https://www.simplilearn.com/tutorials/mysql-tutorial/mysql-workbench-installation

### Prerequisits

- clone the project with HTTPS  
  ```https://github.com/bfhmea4/mea4_03_rides.git```

In order to run the application in docker you need to have Docker Desktop (
Windows): https://docs.docker.com/desktop/install/windows-install/
/ Docker (Mac, Linux): https://docs.docker.com/engine/install/ubuntu/#installation-methods installed

### Making changes

After making changes in the code you have to remove the current container with the following commands, only the
container and the image
containing the changes has to be stopped / removed:

```cmd
docker stop ride-sharing-backend
docker stop ride-sharing-frontend
docker stop ride-sharing-mysql
docker rm ride-sharing-backend
docker rm ride-sharing-frontend
docker rm ride-sharing-mysql
docker rmi ride-sharing-backend
docker rmi ride-sharing-frontend
docker rmi ride-sharing-mysql
```

NOTE: Changes in the DB need to be done in the src/main/db/migration_docker/init.sql file.
After the container is removed, all persisted data will be deleted!

Build and run the application to test the changes made:

```cmd
docker compose up
```

## Testing

There are some unit tests covering the most important functions and processes for the application.
If you want to add new tests add them in src/test/java under the corresponding object.

To run the unit tests, which call the service directly:

```cmd
mvn test
```

Note: It doesn't matter if a local DB exists, the tests will run in a H2 DB created for the test. Testing in the
Docker container is not possible.




  


