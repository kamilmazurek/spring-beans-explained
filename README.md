# Spring Bean Explained: Lifecycle, Scopes, and Practical Examples

Spring Beans are the core building blocks of any Spring Framework or Spring Boot application. A Spring Bean is an object whose creation, dependencies, and lifecycle are managed by the Spring container.

For developers, understanding how beans are instantiated, initialized, and destroyed as well as how to configure their scope is essential for building modular, maintainable, and testable applications.

This repository provides practical examples showing how Spring Beans are created and managed and how their lifecycle and scope can be customized. With clear explanations and hands-on demonstrations, it helps you build clean, modular, and testable Spring Boot applications.

**Overview:**
* **Core Concepts:** What a Spring Bean is and its role in dependency injection.
* **Configuration:** Defining beans with annotations or XML.
* **Scopes:** Singleton, prototype, and other bean scopes.
* **Lifecycle:** Creation, initialization, and destruction of beans.
* **Annotations:** Using `@Component`, `@Service`, `@Repository`, and other Spring stereotypes.
* **Practical Example:** A simple layered architecture example demonstrating Spring Beans in action.

I hope this will help you understand and apply Spring Beans effectively 🙂

## Table of Contents
* [What Is a Spring Bean?](#what-is-a-spring-bean)
* [Why Use Spring Beans?](#why-use-spring-beans)
* [Understanding the Spring IoC Container](#understanding-the-spring-ioc-container)
* [Dependency Injection and Spring Beans](#dependency-injection-and-spring-beans)
* [Configuring Beans with Annotations or XML](#configuring-beans-with-annotations-or-xml)
* [Spring Stereotype Annotations (@Component, @Service, @Repository, etc.)](#spring-stereotype-annotations-component-service-repository-etc)
* [Spring Bean Scopes (Singleton, Prototype, etc.)](#spring-bean-scopes-singleton-prototype-etc)
* [Spring Bean Lifecycle](#spring-bean-lifecycle)
* [Bean Creation and Initialization](#bean-creation-and-initialization)
* [Disclaimer](#disclaimer)

## What Is a Spring Bean?

In Spring, a bean is simply a Java object managed by the Spring IoC (Inversion of Control) container.
Instead of manually creating and wiring objects, you can let Spring handle the instantiation, configuration, dependency injection, and lifecycle of those objects.

In simpler terms, a Spring Bean can be thought of as an object that Spring creates and manages for you.
This management allows developers to focus on business logic rather than on object creation and dependency management.

Spring creates and manages beans based on how developers define them.
One of the most common ways is to use the `@Bean` annotation inside a class annotated with `@Configuration`:

Below is an example that you can find in the `pl.kamilmazurek.example.mybean` package.

```java
@Configuration
public class MyConfiguration {

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

}
```

In this example, `MyConfiguration` defines a Spring Bean by returning a new instance of`MyBean`.
To see what `MyBean` actually looks like, here's its class definition:

```java
@Log4j2
public class MyBean {

    public MyBean() {
        log.info("MyBean instance created");
    }

}
```

Here, `MyBean` is a Spring Bean. When the application starts, Spring detects the `@Configuration` class, calls the `myBean()` method, and registers its return value (`MyBean`) as a managed bean in the IoC container.
Once registered, the bean becomes available for dependency injection throughout the application, so it can be injected into other beans or components, e.g. by using `@Autowired` or constructor injection.

If you want to verify that it works and the bean is actually created, you can start the application with:

```bash
mvnw spring-boot:run
```

You should then see a log entry like this:

```
INFO  pl.kamilmazurek.example.mybean.MyBean: MyBean instance create
```

This approach enables loose coupling, easier testing, and more maintainable applications.

A Spring Bean typically:
* Is a POJO (Plain Old Java Object) registered with the Spring container.
* Has its dependencies injected automatically by Spring.
* Lives within a defined scope (e.g., singleton, prototype, request, session).
* Is configured using annotations like `@Component`, `@Service`, or `@Bean`, or through XML configuration.

## Why Use Spring Beans?

Spring Beans are useful because they allow the Spring Framework to handle object creation, configuration, and lifecycle management, so developers can focus on writing business logic instead of manually wiring objects.

Here’s why I find Spring Beans so helpful:
* **Automatic Dependency Management:** Spring can inject dependencies automatically, removing manual wiring.
* **Loose Coupling:** Letting Spring manage dependencies makes code more modular and easier to maintain.
* **Lifecycle Management:** Spring handles initialization and destruction, so setup or cleanup logic doesn’t clutter business code.
* **Testability:** Beans can be swapped with mocks or stubs for easier unit testing.
* **Scope Flexibility:** Beans can be singleton, prototype, request, or session scoped, giving fine-grained control.
* **Consistent Configuration:** Spring provides a unified way to manage objects via annotations or XML.

Spring Beans are powerful, but their benefits might not be obvious at first. Here is a practical example that starts with a simple `System.out`, then moves to using Spring Beans, and finally exposes functionality through a REST API with just a few lines of code.

Below is an example that you can find in the `pl.kamilmazurek.example.greeting` package.

Let’s say we want our application to display a greeting message.

### Step 1: Using `System.out`
In a plain Java application, you can print a greeting like this:

```java
System.out.println("Hello from the application!");
```

### Step 2: Using a POJO for Flexibility
In real-world applications, it’s common to keep logic flexible and reusable.
Let’s move the greeting logic into a `Greeter` class:

```java
public class Greeter {

    public String createHelloMessage() {
        return "Hello!";
    }

}
```
In a plain Java application, you can use this class like this:

```java
var greeter = new Greeter();
System.out.println(greeter.createHelloMessage());
```

### Step 3: Using Spring Bean
With Spring, the framework can manage the `Greeter` instance for you:

```java
@Configuration
public class GreeterConfiguration {

    @Bean
    public Greeter greeter() {
        return new Greeter();
    }

}
```
Spring will create the `Greeter` instance, manage its lifecycle, and make it available for dependency injection wherever it is needed.

Now that `Greeter` is Spring-managed, we can use it in other components. Next, we’ll see how to expose its functionality through a REST API.

### Step 4: Exposing the Greeting via REST API
With Spring Boot, we can now expose the greeting through a REST endpoint:

```java
@RestController
public class GreeterRestController {

    private final Greeter greeter;

    public GreeterRestController(Greeter greeter) {
        this.greeter = greeter;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return greeter.createHelloMessage();
    }

}
```

To make this work, you can use Spring Boot Web Starter, for example, by including the dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

With just a few lines of code, this simple POJO is now managed by Spring, with automatic dependency injection and lifecycle management.
This lets developers focus on writing business logic and reusable components while Spring handles wiring and managing beans behind the scenes.

This approach makes the code easier to maintain, test, and extend, and allows you to use many Spring mechanisms along with their related libraries and solutions, for example, to expose functionality like the greeting message through a REST API without much effort.

To see this in action, run the application with:

```bash
mvnw spring-boot:run
```

Then open the following URL in your browser:

```bash
http://localhost:8080/api/greetings/hello
```

You should see the **Hello!** message displayed 🙂

## Understanding the Spring IoC Container

The Spring IoC Container is a core component of the Spring Framework that creates, manages, and wires beans.
Instead of manually instantiating and linking objects, the container handles it automatically.

It follows the Inversion of Control (IoC) principle, where the framework, rather than the application, is responsible for object creation and dependency injection.
This approach keeps code focused on business logic and makes it more modular, testable, and maintainable.

### Why the Spring IoC Container Is Useful
* **Automatic Object Instantiation:** Creates and configures beans based on annotations, XML, or Java classes.
* **Dependency Injection (DI):** Injects required dependencies into beans so classes don’t have to construct or locate them manually.
* **Lifecycle Handling:** Manages bean initialization and, where applicable, destruction.
* **Scope Flexibility:** Supports singleton, prototype, request, or session scopes depending on application needs.

I think of the IoC container as a combination of a “bean factory” and a “dependency manager.”
Developers define which beans exist and how they depend on each other, and the container takes care of their creation, management, and wiring automatically.

## Dependency Injection and Spring Beans

One of the main benefits of the Spring IoC Container is dependency injection (DI), which allows an object’s dependencies to be provided from the outside, rather than having the object create them itself.

Dependencies between beans can be configured in multiple ways, including:
* **Constructor Injection:** Providing dependencies via the bean’s constructor.
* **Injection with `@Autowired`:** Spring injects the required bean via a setter, field, or constructor.
* **Java Configuration:** Defining beans in a `@Configuration` class so Spring can manage their dependencies.
* **XML Configuration:** Declaring beans and their dependencies in an XML file using `<bean>` elements with `property` or `constructor-arg`.

To better understand the concept, let’s see how it can be done using a `@Configuration` class.

Below is an example that you can find in the `pl.kamilmazurek.example.time` package.

Suppose we have a simple `TimeProvider` class that returns the current time:

```java
public class TimeProvider {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

}
```

Next, we want a `TimeLogger` class that uses `TimeProvider` to log the current time:

```java
@Slf4j
public class TimeLogger {

    private final TimeProvider timeProvider;

    public TimeLogger(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public void logCurrentTime() {
        log.info("Current time: " + timeProvider.now());
    }

}
```

We can write a configuration class to tell Spring to inject the `TimeProvider` bean into the `TimeLogger` bean, like this:

```java
@Configuration
public class TimeConfiguration {

    @Bean
    public TimeProvider timeProvider() {
        return new TimeProvider();
    }

    @Bean
    public TimeLogger timeLogger(TimeProvider timeProvider) {
        return new TimeLogger(timeProvider);
    }

}
```

Here, the Spring IoC Container:
* Creates a `TimeProvider` bean.
* Creates a `TimeLogger` bean and automatically injects the `TimeProvider` into it.

There is also a `Runner` class that calls the `logCurrentTime()` method, so the current time will be logged during application startup:

```java
@Component
@AllArgsConstructor
public class Runner implements ApplicationRunner {

    private final TimeLogger timeLogger;

    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        timeLogger.logCurrentTime();
        userService.logExistingUsers();
    }

}
```

To see it in action, start the application:

```bash
mvnw spring-boot:run
```

You should then see a log entry similar to the following:

```
INFO  pl.kamilmazurek.example.time.TimeLogger: Current time: 2025-11-23T22:23:59.74784280
```

The classes don’t construct or pass dependencies themselves. They simply declare what they need, and the IoC container provides it.

This approach reduces boilerplate, increases modularity, and simplifies testing by allowing dependencies to be replaced with mocks or stubs.
The IoC container manages beans automatically, letting developers focus on business logic

## Configuring Beans with Annotations or XML
Spring provides several ways to define and configure beans, allowing developers to choose the approach that best fits their application’s complexity and design preferences.
The three most common methods are:
* Annotation-based configuration
* Java-based configuration using `@Configuration` and `@Bean`
* XML-based configuration files

Although XML configuration remains supported for legacy applications,
modern Spring Boot projects typically favor annotation-driven and Java-based configurations for better readability, maintainability, and seamless integration with auto-configuration.

### Annotation-Based Configuration

Annotation-based configuration is the most common and convenient way to define beans in modern Spring applications.
Developers can mark classes with specific annotations, allowing Spring to automatically detect and register them as beans during component scanning.

To achieve this, developers typically use [Spring Stereotypes](#spring-stereotype-annotations-component-service-etc), such as:
* **@Component:** Generic stereotype for any Spring-managed component.
* **@Service:** Specialization of `@Component` used to mark classes that hold business logic.
* **@Repository:** Indicates a component that handles data access and enables automatic exception translation.
* **@Controller:** Identifies a class that handles web requests in Spring MVC applications.
* **@RestController:** Combination of `@Controller` and `@ResponseBody`, typically used for RESTful web services.

Below is an example from the `pl.kamilmazurek.example.user` package that demonstrates how to define a `UserService` and inject a `UserRepository` into it using annotations and constructor-based injection.

In this example, the `UserService` receives a `UserRepository` through its constructor. It then fetches all existing users and logs their usernames:

```java
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logExistingUsers() {
        var users = userRepository.findAll().stream().map(UserEntity::getLogin).toList();
        log.info("Existing users: " + String.join(", ", users));
    }

}
```

Below is the corresponding `UserRepository`, a simple Spring Data JPA interface used to access `UserEntity` records:

```java
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
```

Below is the `UserEntity` class, a simple JPA entity representing a user record in the `users` table:

```java
@Entity
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;

    private String login;

}
```

This example uses Spring Data JPA, added by the following Maven dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

For simplicity, the example runs on an in-memory H2 database, with Spring Boot configured to use H2 and load initial data from `users.sql`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:spring-beans-explained
  jpa:
    defer-datasource-initialization: true
  sql:
    init:
      data-locations:
        - classpath*:users.sql
```

Additionally, similar to the previous example, there is a`Runner` class that calls the `logExistingUsers()` method, so the existing users be logged during application startup:

```java
@Component
@AllArgsConstructor
public class Runner implements ApplicationRunner {

    private final TimeLogger timeLogger;

    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        timeLogger.logCurrentTime();
        userService.logExistingUsers();
    }

}
```

As a result, after running the application with `mvnw spring-boot:run`, you should see a log entry like:

```
INFO  pl.kamilmazurek.example.user.UserService: Existing users: test-user-a, test-user-b, test-user-c
```

**Note:** Spring Boot automatically performs component scanning for the package containing the main application class (and its subpackages).

However, if you need to manually enable automatic detection of annotated classes, you can use the `@ComponentScan` annotation:

```java
@Configuration
@ComponentScan(basePackages = "package.goes.here")
public class AppConfig {
}
```

Annotation-based configuration offers a lightweight, easy-to-start approach that fits naturally within microservice architectures.
That said, in very large applications, extensive use of automatic scanning can make it harder to trace dependencies and manage configurations explicitly.

### Java-Based Configuration with @Configuration and @Bean

Java-based configuration defines beans using dedicated configuration classes annotated with `@Configuration` and methods annotated with `@Bean`.
This approach provides a clear and type-safe way to configure beans without relying on XML or component scanning.

As shown in the [Dependency Injection and Spring Beans](#dependency-injection-and-spring-beans) section, the `TimeConfiguration` class in the `pl.kamilmazurek.example.time` package demonstrates how to define beans and wire their dependencies using `@Bean` methods.

```java
@Configuration
public class TimeConfiguration {

    @Bean
    public TimeProvider timeProvider() {
        return new TimeProvider();
    }

    @Bean
    public TimeLogger timeLogger(TimeProvider timeProvider) {
        return new TimeLogger(timeProvider);
    }

}

```

Java-based configuration gives developers explicit control over bean creation and wiring.
It is particularly useful when integrating third-party libraries, applying custom initialization logic, or when precise control over the bean lifecycle is needed.

### XML-Based Configuration

In the early versions of Spring, beans were typically defined in XML configuration files using the `<bean>` element.

This approach allowed developers to explicitly declare each bean and its dependencies.

The XML configuration below reflects the same setup as the previously shown `TimeConfiguration` example:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
           http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="timeProvider" class="pl.kamilmazurek.example.time.TimeProvider"/>

    <bean id="timeLogger" class="pl.kamilmazurek.example.time.TimeLogger">
        <constructor-arg ref="timeProvider"/>
    </bean>

</beans>
```

Depending on the application setup, an XML configuration file can be loaded in several ways. For example:

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
TimeLogger timeLogger = context.getBean("timeLogger", TimeLogger.class);
timeLogger.logCurrentTime();
```

With this configuration, the Spring IoC container:
* Creates a `TimeProvider` bean.
* Creates a `TimeLogger` bean and injects the `TimeProvider` into it via the constructor.

XML-based configuration offers clarity and flexibility, as all beans and dependencies are explicitly defined.
However, it can become verbose and harder to maintain as the application grows, which is why most modern Spring Boot projects prefer annotation-driven or Java-based configurations.

### Which Configuration Type Should Be Used?

Spring provides flexibility in how beans are defined and managed, with each configuration style offering its own advantages and ideal use cases.

Annotation-based configuration is the preferred approach for most modern Spring applications.
It allows concise definitions, integrates seamlessly with Spring Boot’s auto-configuration, and promotes a clean, component-oriented structure that is easy to read and maintain.

Java-based configuration complements annotation-driven approaches by providing explicit, type-safe bean definitions in code.
It is particularly useful when integrating third-party libraries, adding custom initialization logic, or when fine-grained control over bean creation is needed.

XML-based configuration provides explicit control and works well for legacy systems or when integrating with older Spring applications.
However, it can become verbose and harder to maintain as a project grows.

In modern Spring Boot development, annotation-driven and Java-based configurations are considered the standard.
For now, XML is still supported, but is mainly used in legacy systems or specific integration scenarios.

## Spring Stereotype Annotations (@Component, @Service, @Repository, etc.)

Spring stereotype annotations are special markers for classes. They indicate that the class is managed by the Spring container.
These annotations allow Spring to automatically detect and register classes as beans during component scanning, reducing the need for explicit configuration.

A basic building block of this mechanism is the `@Component` annotation, which serves as a generic stereotype for any Spring-managed component.
Spring also provides specialized annotations built on top of `@Component`. These help organize classes by their responsibilities, making the application structure clearer and easier to maintain.

The following example from the `pl.kamilmazurek.example.order` package demonstrates how beans of different types, defined with multiple stereotypes, work together in practice.

### @Component
Marks a class as a general-purpose Spring bean. It is the base stereotype for all other specialized annotations.

```java
@Component
public class OrderValidator {

    public boolean isValid(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return false;
        }

        return hasProducts(orderEntity);
    }

    private boolean hasProducts(OrderEntity orderEntity) {
        return orderEntity.getProducts() != null && !orderEntity.getProducts().isEmpty();
    }

}
```

### @Repository
Marks a class as a data access or persistence layer component.
Spring can automatically translate persistence-related exceptions thrown by these classes into its unified `DataAccessException` hierarchy.

```java
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
```

For a bigger picture, sample `OrderEntity` may look as follows:

```java
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private Long id;

    private LocalDate orderDate;

    private double totalAmount;

    @ElementCollection
    @Column(name = "product")
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    private List<String> products;


}
```

### @Service
A specialization of `@Component` used for classes containing business logic or service-related operations.
This helps organize large applications and makes their structure easier to understand.

```java
@Service
public class OrderService {

    private final OrderValidator orderValidator;

    private final OrderRepository orderRepository;

    public OrderService(OrderValidator orderValidator, OrderRepository orderRepository) {
        this.orderValidator = orderValidator;
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }

    public List<OrderEntity> getValidOrders() {
        return orderRepository.findAll().stream().filter(orderValidator::isValid).toList();
    }

}
```

### @Controller
Marks a class as a web controller in a Spring MVC application.
Methods inside typically handle HTTP requests and return views.

```java
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public ModelAndView showOrders() {
        var modelAndView = new ModelAndView("orders");

        modelAndView.addObject("name", "Orders");
        modelAndView.addObject("orders", orderService.getOrders());

        return modelAndView;
    }

    @GetMapping("/valid")
    public ModelAndView showValidOrders() {
        var modelAndView = new ModelAndView("orders");

        modelAndView.addObject("name", "Valid Orders");
        modelAndView.addObject("orders", orderService.getValidOrders());

        return modelAndView;
    }

}
```

The controller provides data to the template, which Spring renders on the server before sending the final HTML to the browser.

A template engine can be used to render the view, for example Thymeleaf.
In a Spring Boot–based application, this can be configured by adding the following dependency to `pom.xml`:

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```

In this example, the template is placed in `src/main/resources/templates/orders.html` and uses a CSS stylesheet from `src/main/resources/static/css/styles.css`.
The same template is used for both all orders and valid orders, with the displayed data changing based on the controller method that provides it.

When the application is running, the results can be viewed at the following addresses:
* All orders: `http://localhost:8080/orders`
* Valid orders: `http://localhost:8080/orders/valid`

The template will display either all orders or only valid orders, depending on the endpoint used.
The controller provides the data by retrieving it from the service, which in turn obtains it from the repository that reads it from the database.

### @RestController
A convenient specialization of `@Controller` that combines `@Controller` and `@ResponseBody`.
It is commonly used in RESTful web services to return data directly as `JSON` or `XML`.

```java
@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/valid")
    public List<OrderEntity> getValidOrders() {
        return orderService.getValidOrders();
    }

}
```

Once the application is running, you can access the REST endpoints in your browser or any HTTP client:
* All orders: `http://localhost:8080/api/orders`
* Valid orders: `http://localhost:8080/api/orders/valid`

The RestController retrieves the data from the service, which obtains it from the repository that reads it from the database.
The server then returns the result in `JSON` format.

---

These stereotype annotations help organize an application’s structure by clearly defining the responsibilities of different components such as controllers, services, and repositories.
They make the application easier to understand and maintain by providing meaningful context about each component’s role within the overall architecture.

## Spring Bean Scopes (Singleton, Prototype, etc.)

In Spring, bean scope determines how many instances of a bean are created and how long they live within the application context.
Understanding scopes is essential for managing state, memory usage, and lifecycle behavior of your beans.

Spring provides several built-in bean scopes, giving developers fine-grained control over how and when bean instances are created:
* **Singleton:** The default scope in Spring, with only one shared instance per container. Useful for stateless services and shared resources.
* **Prototype:** A new instance is created every time the bean is requested. Useful for stateful or temporary objects.
* **Request:** A new instance is created for each HTTP request. Useful for request-specific data.
* **Session:** One instance per HTTP session, shared across multiple requests from the same user. Useful for user-specific state.
* **Application:** One instance per web application context, shared across all requests and sessions. Useful for global resources or caches.
* **WebSocket:** One instance per WebSocket session. Useful for maintaining session-specific state in real-time applications.

### Singleton Scope

Singleton is the default scope in Spring. Only one shared instance of the bean is created per Spring IoC container.
All requests for this bean will return the same instance.

For example, consider a `SingletonService` bean:

```java
@Service
public class SingletonService {

    public void doSomething(){
        // place for some logic
    }

}
```

`SingletonService` can be injected into multiple other beans, such as `OtherServiceA` and `OtherServiceB`:

```java
@Service
public class OtherServiceA {

    private final SingletonService singletonService;

    public OtherServiceA(SingletonService singletonService) {
        this.singletonService = singletonService;
    }

}
```

```java
@Service
public class OtherServiceB {

    private final SingletonService singletonService;

    public OtherServiceB(SingletonService singletonService) {
        this.singletonService = singletonService;
    }

}
```

In this case both `OtherServiceA` and `OtherServiceB` will share the same instance of `SingletonService`.

Notes about singleton beans:
* Useful for stateless beans and services.
* Created eagerly at container startup by default.
* Shared across the application, which can save memory and simplify dependency management.

### Prototype Scope

Prototype beans are created anew each time they are requested from the Spring container.
Unlike singleton beans, every injection or retrieval results in a fresh instance.
For example, consider a `PrototypeService` bean:

```java
@Service
@Scope("prototype")
public class PrototypeService {

    public void doSomething() {
        // place for some logic
    }

}
```

`PrototypeService` can be injected into multiple other beans, such as `OtherServiceA` and `OtherServiceB`:

```java
@Service
public class OtherServiceA {

    private final PrototypeService prototypeService;

    public OtherServiceA(PrototypeService prototypeService) {
        this.prototypeService = prototypeService;
    }
}
```

```java
@Service
public class OtherServiceB {

    private final PrototypeService prototypeService;

    public OtherServiceB(PrototypeService prototypeService) {
        this.prototypeService = prototypeService;
    }

}
```

In this case, `OtherServiceA` and `OtherServiceB` will each receive a separate instance of `PrototypeService`.

Notes about prototype beans:
* Useful for stateful or temporary objects.
* Created on demand, not eagerly at container startup.
* Each consumer gets a new instance.

### Request Scope

The request scope is specific to web applications. A new instance of the bean is created for each HTTP request.
This ensures that each request gets its own independent instance, making it suitable for request-specific data.

For example, consider a `RequestService` bean:

```java
@Service
@RequestScope
public class RequestService {

    public void processRequest() {
        // logic specific to a single HTTP request
    }

}
```

If `RequestService` is injected into multiple components handling the same request, they share the same instance for that request.
However, a new HTTP request will receive a new instance.

```java
@RestController
@RequestMapping("/api")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/process")
    public String handle() {
        requestService.processRequest();
        return "Request processed";
    }

}
```

Notes about request-scoped beans:
* Created once per HTTP request.
* Useful for beans that hold request-specific state.
* Shared only within the same request.
* Requires a web-aware Spring context (e.g., Spring MVC or Spring WebFlux).

### Session Scope

The session scope is used in web applications where a bean needs to be shared across multiple HTTP requests within the same user session.
A new instance of the bean is created once per HTTP session and is reused for all requests from that session.
This is useful for user-specific state, such as shopping carts, user preferences, or temporary session data.

For example, consider a `SessionService` bean:

```java
@Service
@SessionScope
public class SessionService {

    private int counter = 0;

    public int incrementCounter() {
        counter++;
        return counter;
    }

}
```

When SessionService is injected into different components handling requests from the same user session, they share the same instance.
Another user with a different session will get a separate instance.

```java
@RestController
@RequestMapping("/api")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/count")
    public String count() {
        int value = sessionService.incrementCounter();
        return "Counter for this session: " + value;
    }

}
```

Notes about session-scoped beans:
* Created once per HTTP session.
* Shared across multiple requests in the same session.
* Suitable for user-specific data that persists during the session.
* Requires a web-aware Spring context.

### Application Scope

Application scope is useful for beans that need to be shared across a web application.
A single instance is created per ServletContext and shared across all requests and sessions within that ServletContext.
This makes it useful for global resources such as caches, configuration settings, or shared services.

For example, consider an `ApplicationService` bean:

```java
@Service
@ApplicationScope
public class ApplicationService {

    private int globalCounter = 0;

    public int incrementCounter() {
        globalCounter++;
        return globalCounter;
    }

}
```

If `ApplicationService` is injected into multiple components, all requests and sessions share the same instance:

```java
@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/global-count")
    public String globalCount() {
        int value = applicationService.incrementCounter();
        return "Global counter: " + value;
    }

}
```

Notes about application-scoped beans:
* Created once per web application context.
* Shared across all requests and sessions.
* Suitable for global resources such as caches, configuration objects, or shared services.
* Requires a web-aware Spring context.

### WebSocket Scope

The WebSocket scope is used in Spring applications with WebSocket support.
A new bean instance is created for each WebSocket session and is shared across all interactions within that session.
This is useful for maintaining session-specific state in real-time applications, such as chat sessions, live notifications, or user-specific streaming data.

For example, consider a `WebSocketService` bean:

```java
@Service
@WebSocketScope
public class WebSocketService {

    private final List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

}
```

If `WebSocketService` is injected into multiple WebSocket handlers for the same session, they share the same instance.
Each new WebSocket session gets a separate instance, isolating state between sessions.

```java
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketService webSocketService;

    public ChatWebSocketHandler(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        webSocketService.addMessage(message.getPayload());
    }

}
```

Notes about WebSocket-scoped beans:
* Created once per WebSocket session.
* Shared across all handlers within the same session.
* Useful for session-specific state in real-time applications.
* Requires a WebSocket-aware Spring context.

### When to Use Each Scope
* **Singleton:**
    * Useful for stateless services, shared utilities, or beans that do not maintain per-request state.
    * Example: A `NotificationService` that sends emails or push notifications, where a single shared instance is sufficient for the entire application.
* **Prototype:**
    * Useful when a new instance is needed each time the bean is requested.
    * Example: A `ReportGenerator` bean that holds temporary data while creating a report. Each request (for example, via `ObjectProvider`) should obtain a fresh instance.
* **Request:**
    * Useful for web applications where a bean should exist only for a single HTTP request.
    * Example: A `UserRequestLogger` that collects request-specific info for auditing or metrics.
* **Session:**
    * Useful for maintaining user-specific state across multiple HTTP requests.
    * Example: A `ShoppingCart` bean that keeps track of items a user adds during their session.
* **Application:**
    * Useful for global resources shared across the entire web application.
    * Example: A `CacheManager` or `ApplicationConfig` bean that stores configuration or cached data accessible to all users.
* **WebSocket:**
    * Useful for WebSocket sessions where state persists during real-time communication.
    * Example: A `ChatSessionService` that maintains the message history for each user during a live chat session.

Personally, I like to choose the bean scope based on how it will be used: singleton for shared, stateless services, and request, session, or prototype scopes only when per-instance or per-user state is needed.
This approach helps me avoid memory issues, keeps things thread-safe, and makes the code easier to manage.

## Spring Bean Lifecycle

Every Spring bean managed by the IoC container follows a lifecycle that defines how it is created, initialized, and eventually destroyed, helping developers manage resources, add custom logic, and troubleshoot initialization issues effectively.

When the Spring application context starts, it scans, instantiates, and wires all defined beans.
Each bean then passes through several stages before it’s ready for use, and the reverse happens when the context shuts down.

The Spring bean lifecycle for singleton beans includes the following steps:
1. **Instantiation** : The container creates a new instance of the bean, typically by calling its constructor or a factory method.
2. **Dependency Injection** : The container injects the bean’s dependencies, setting properties or constructor arguments declared in the configuration.
3. **Aware Interfaces (optional)** : If the bean implements interfaces such as `BeanNameAware`, `BeanFactoryAware`, or `ApplicationContextAware`, the container provides relevant context information.
4. **BeanPostProcessor (Before Initialization, optional)** : Any registered `BeanPostProcessors` are applied before the bean’s initialization callbacks.
5. **Initialization** : The Spring container calls configured initialization logic for the bean. This can include methods annotated with `@PostConstruct`, the `afterPropertiesSet()` method from `InitializingBean`, or a custom `init-method` defined in configuration.
6. **BeanPostProcessor (After Initialization, optional)** : Any registered `BeanPostProcessors` are applied after the bean’s initialization callbacks.
7. **Ready for Use** : The bean is fully initialized and available for injection or retrieval from the container.
8. **Destruction** : When the application context is closed, Spring invokes destruction callbacks. This can include methods annotated with `@PreDestroy`, the `destroy()` method from `DisposableBean`, or a custom `destroy-method` defined in configuration. Prototype beans are not automatically destroyed by the container.

**Note:** Prototype beans are **not automatically destroyed** by the container.

### Conceptual Flow
Before diving into the implementation details, it helps to see the entire lifecycle at a glance.
The simplified flow below provides a high-level view of how a Spring bean transitions through its key lifecycle phases (for singleton beans):

```
Instantiation → Dependency Injection → Aware Interfaces → BeanPostProcessor (Before Initialization) → Initialization → BeanPostProcessor (After Initialization) → Ready for Use → Destruction
```

During these phases, Spring allows developers to hook into the bean lifecycle using annotations (e.g., `@PostConstruct`, `@PreDestroy`), interfaces (e.g., `InitializingBean`, `DisposableBean`), or configuration options (custom `init-method` and `destroy-method`) to execute custom logic.  

These mechanisms are explained in the next sections:
* [Bean Creation and Initialization](#bean-creation-and-initialization)
* [Bean Destruction and Cleanup](#bean-destruction-and-cleanup).

## Bean Creation and Initialization

After a Spring bean is instantiated and its dependencies are injected, the Spring container calls any configured initialization logic, allowing the bean to be fully ready for use within the application.

Spring provides several mechanisms to handle initialization, giving developers flexibility in applying custom logic.

### Initialization Using Annotations
Using the `@PostConstruct` annotation is a common choice for initialization in many modern Spring applications.

This annotation marks a method to run after the bean is created and dependencies are injected:

```java
@Component
public class SomePostConstructAnnotatedBean {

  @PostConstruct
  public void init() {
    System.out.println("SomePostConstructAnnotatedBean is ready to use");
  }

}
```

I often choose this approach because it keeps the bean decoupled from Spring-specific interfaces, which makes the code cleaner and easier to maintain.

### Initialization Using Interfaces
Spring also offers the `InitializingBean` interface, which allows a bean to execute custom logic after its properties are set by the container.

To use it, implement the `afterPropertiesSet()` method:

```java
@Component
public class SomeInitializingBean implements InitializingBean {

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("SomeInitializingBean has been initialized");
  }

}
```

The Spring container calls `afterPropertiesSet()` after dependencies have been injected and after any before-initialization BeanPostProcessor callbacks.

This approach is useful when you want initialization logic that directly integrates with the Spring framework.

### Initialization via @Bean Methods

For beans defined in Java configuration classes, you can specify an `initMethod` that Spring will call after the bean has completed `afterPropertiesSet()`:

```java
@Configuration
public class AppConfig {

  @Bean(initMethod = "customInit")
  public MyCustomInitBean myCustomInitBean() {
    return new MyCustomInitBean();
  }

}
```

And in the `MyCustomInitBean` class:

```java
public class MyCustomInitBean {

  public void customInit() {
    System.out.println("Custom initialization logic executed");
  }

}
```

I find this especially useful when integrating third-party classes that cannot be annotated with `@PostConstruct`.
You can either create your own initialization method or tell Spring to call an existing method after the bean is created.
This approach works well for classes you cannot or prefer not to modify.

Please note that the same can also be done using XML configuration, which can be especially useful for legacy systems, for example:

```xml
<bean id="myCustomInitBean" class="pl.kamilmazurek.example.MyCustomInitBean" init-method="customInit"/>
```

### Choosing the Right Way to Initialize a Bean

After a Spring bean is instantiated and its dependencies are injected, the Spring container calls any configured initialization logic in a defined order, allowing the bean to become fully ready for use.

You might want to run custom initialization logic in different situations:
* **Annotating a method with `@PostConstruct`:** I like this approach because it keeps the bean decoupled from Spring-specific interfaces, making the code cleaner and easier to maintain.
* **Implementing `InitializingBean`:** This can be a good choice when you want your initialization logic to be tightly integrated with Spring and don’t mind depending on a Spring-specific interface.
* **Specifying `initMethod`:** This can be useful when working with third-party classes or beans you don’t want to modify. You can either create your own initialization method or tell Spring to call an existing method after the bean is created.

Choosing the right approach depends on your project style and whether you prefer annotations, interfaces, or configuration-based control.
For me, `@PostConstruct` provides a simple and maintainable way to handle initialization in most modern Spring Boot applications.

## Disclaimer

THIS SOFTWARE AND ANY ACCOMPANYING DOCUMENTATION (INCLUDING, BUT NOT LIMITED TO, THE README.MD FILE) ARE PROVIDED
FOR EDUCATIONAL PURPOSES ONLY.

THE SOFTWARE AND DOCUMENTATION ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE,
THE DOCUMENTATION, OR THE USE OR OTHER DEALINGS IN THE SOFTWARE OR DOCUMENTATION.

Spring is a trademark of Broadcom Inc. and/or its subsidiaries. Spring Boot is a trademark of Broadcom Inc. and/or its subsidiaries.
Oracle, Java, MySQL, and NetSuite are registered trademarks of Oracle and/or its affiliates. Other names may be trademarks of their respective owners.