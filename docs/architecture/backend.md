# Backend

Our application's backend is built with the Spring framework. Spring is a Java platform that provides wide ranged
support and infrastructure for developing applications with java, allowing the developers to stay focused on the application.
There about 20 modules in the Spring Framework, grouped into Core Container, Data Access/Integration, Web, Aspect
Oriented Programming, Instrumentation and Test. Core and Beans represent the fundamental elements of the Framework.
See [Spring Documentation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/overview.html)
for more details.

## Structure

Our Backend Web Service is packed in the `src` folder.

There is a subdirectory for `advices` which enables a mechanism to tell the application which http code to return with
the response when a certain exception is thrown.

The `config` directory contains the required config files for the logger and some CORS settings to add multiple Origins
for our application to receive requests. We also implemented the Spring security module for authentication and password
encoding.

Authentication is done with a JWT Token configured in the `security` folder.

Next to the `controller` directory (in which the content should be clear) there is a `database` folder
containing all the entity-model and repository classes which are our API to our DB. Together with the
multiple services interface. Spring (with the correct annotations: `@Entity, @RestController, @Service` and repository
interfaces implementing the `CrudRepository`) is able to build a functioning REST API without the need to manually write
a JDBC API including the queries.

The `dto` objects, which store only essential data, are used to transfer objects from and
to our web application. There are `filters` for Login, CORS and authentication in the corresponding directory.

## Core and Beans

Core and Beans modules provide the fundamental parts of the framework, including the Inversion of Control (IoC) /
Dependency Injection (DI) features.

### IoC Container

The `ApplicationContext` Interface (a sub Interface of the `BeanFactory`) in Spring represents the Spring IoC Container.
The container gets its instructions what objects to instantiate, configure and assemble by reading the configuration
metadata. In our application these instructions are given by certain annotations like `@Bean`, `@RestController`,
`@Service` etc.

### Beans

The `BeanFactory`, an implementation of the factory pattern, remove the need to write singletons and allows the
separation of the configuration and specification from the actual business logic.
Objects that build the backbone of the application, are called beans in Spring. They are managed by the IoC Container.
Beans and the dependencies between them are reflected in the metadata configuration used by a container.

## Authentication

The Authentication and Authorization in our application are done with a `JWT Token`. Next to our login and register 
endpoints all others are only reachable with a valid JWT Token which is checked in the LoginFilter.

