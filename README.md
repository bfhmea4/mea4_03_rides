# mea4_03_rides
##Ride Sharing Platform

## Running the application locally

- clone the project with HTTPS  
   ```https://github.com/bfhmea4/mea4_03_rides.git```

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

There is a Dockerfile which is proceeded to a Docker image
in the pipeline. You can run the image with the following command
docker run -p 8080:8080 --rm ride-sharing-platform