# mea4_03_rides
##Ride Sharing Platform

## Running the application locally

### Prerequisits

  In order to run the application locally you should be able to run the
  following technologies:
  - maven (https://maven.apache.org/install.html)
  - angular (https://angular.io/guide/setup-local)
  - mysql (https://dev.mysql.com/downloads/installer/)

  - clone the project with HTTPS  
     ```https://github.com/bfhmea4/mea4_03_rides.git```

## How to use a local database:

Make sure you have a database with the correct name and port, as well as username and password as configured in application-local.properties:
according to https://flywaydb.org/documentation/configuration/parameters/
Username properties: spring.flyway.user, flyway.user, spring.datasource.username
Password properties: spring.flyway.password, flyway.password, spring.datasource.password
Database name: spring.flyway.schemas, flyway.schemas
Location of database (URL): spring.flyway.url, flyway.url, spring.datasource.url
- example: jdbc:mysql://localhost:[port]/database_name

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
  mvn spring-boot:run
  ```
- After the startup the application is runs on port 8080

### Running the Webapp
- open a command line tool of your choice and navigate to the root folder
  of the webbapp "\mea4_03_rides\src\main\ride_sharing_webbapp".
- start the angular application:  
  ```ng serve```
- After the startup the webbapp will run on port 4200
- visit "localhost:4200"

## How to start the application using docker:

There is a Docker compose file which builds the application in a container group and starts it
in the pipeline. 
Build and run the application: 
```cmd
docker compose up
```
Note: It is likely you have to restart the backend container, if the DB image has not yet started, just rerun the image on the Docke GUI or with command
```cmd
docker compose up
```


