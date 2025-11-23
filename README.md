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