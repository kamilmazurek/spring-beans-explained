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
You should then see a log entry similar to the following:
```
2025-11-22T19:42:05.973+01:00  INFO 4204 --- [           main] pl.kamilmazurek.example.mybean.MyBean    : MyBean instance created
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