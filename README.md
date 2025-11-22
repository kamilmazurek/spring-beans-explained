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

This approach enables loose coupling, easier testing, and more maintainable applications.

A Spring Bean typically:
* Is a POJO (Plain Old Java Object) registered with the Spring container.
* Has its dependencies injected automatically by Spring.
* Lives within a defined scope (e.g., singleton, prototype, request, session).
* Is configured using annotations like `@Component`, `@Service`, or `@Bean`, or through XML configuration.

## Disclaimer

THIS SOFTWARE AND ANY ACCOMPANYING DOCUMENTATION (INCLUDING, BUT NOT LIMITED TO, THE README.MD FILE) ARE PROVIDED
FOR EDUCATIONAL PURPOSES ONLY.

THE SOFTWARE AND DOCUMENTATION ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE,
THE DOCUMENTATION, OR THE USE OR OTHER DEALINGS IN THE SOFTWARE OR DOCUMENTATION.

Spring Boot is a trademark of Broadcom Inc. and/or its subsidiaries.
Oracle, Java, MySQL, and NetSuite are registered trademarks of Oracle and/or its affiliates. Other names may be trademarks of their respective owners.