# Frontend

Our application's frontend is build with Angular. Angular is a platform and framework for building single-page
client applications using HTML and Typescript. Its functionalities are implemented as a set of TypeScript libraries
which
can be imported in the application. As a platform, Angular includes a component-based framework for building scalable
web applications from a single-developer project to enterprise level-applications. It contains also a suite of tools to
help develop, build and test the code.

## Structure

Our angular web application is packed in the `ride_sharing_webapp` folder.
The code itself is stored in the `src` directory which contains the `app` directory containing the root component 
(app.component) containing all the components created. 
The `app.module.ts` manages all imported packages and declares the components created. 
The `app-routing.module.ts` tracks the routes and the corresponding components using Angular's RouterModule.
The src includes also the service and the model directory, as well as the environments directory with all the URLs 
reachable in the application. Additionally, a styles.css with styles used across all components and the index.html 
which is the frame for all the components rendered. Next to the src directory there are multiple files created by the 
angular framework. For example the package.json which include all packages used by the application. If a new package is 
added the `npm install` command is used to install the packages defined here.

### Components

An Angular web-application consists of Components which are visible in the app folder. Components are the main building
block of Angular applications. Each component consists of an HTML template that holds the parts to render on the page,
a TypeScript class defining the behaviour, a CSS selector that defines how the component is used in a template and,
optionally a CSS style sheet to add styling to the rendered parts. Each component has its own directory in the app
folder and can be created with the `ng generate c [name]` command.

### Templates

The components HTML template, hold the parts to render on the page. The templates accept a lot of new functionality
offered by the Angular framework. For example if a list of ride-offers is stored in the components code, it can easily
be traversed and displayed in the template with the `*ngFor` statement or a tag can be omitted depending on a variables
value with the `*ngIf` statement. These are called `structural directives`. To access objects in Templates `NgModel`
adds
a two-way data binding to an HTML form element. Adding and removing styles as well as CSS classes can be done with
`NgClass` respectively `NgStyle`. These are called `attribute directives`.

### Dependency Injection

Dependency Injection or DI is a fundamental concept in Angular. It allows classes e.g. components to configure
dependencies needed. On the bootstrap process a application-wide injector (root injector) is created to configure global
dependencies. 
For example the create-update-ride-offer uses the ride-offer service as well as the authentication service.
It is enough to declare the service in the constructor of the components TypeScript file and Angular checks if there is
already an instance of the services and creates one if there isn't.

