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

## How to migrate the database:

Make sure you have a database with the correct name and port, as well as username and password as configured in application.properties, 
according to https://flywaydb.org/documentation/configuration/parameters/ 
Run the following command to migrate the database:
```cmd
mvn clean -Dflyway.configFiles=src/main/resources/application.properties flyway:migrate
```

### Running the WebService
- open a command line tool of your choice and navigate to the root folder
of the project "\mea4_03_rides".
- Test if the application is runnable the following command:  
  ```mvn install```
- Run the application:  
  ```mvn spring-boot:run```
- After the startup the application is runs on port 8080
- Test the api with making a request through a web browser for example:  
  http://localhost:8080/fizzbuzz/3

### Running the Webapp
- open a command line tool of your choice and navigate to the root folder
  of the webbapp "\mea4_03_rides\src\main\ride_sharing_webbapp".
- start the angular application:  
  ```ng serve```
- After the startup the webbapp will run on port 4200
- visit "localhost:4200"
- there you can test the connection between frontend and backend works,
using FizzBuzz.

## How to start the application using docker:

- There is a Dockerfile which is proceeded to a Docker image
in the pipeline. 
- Pull the image from Docker Hub: 
```docker pull jenkis94/mea4_03_rides:latest```
- Run the image with the following command:
```docker run -p 8080:8080 --rm jenkis94/mea4_03_rides```
