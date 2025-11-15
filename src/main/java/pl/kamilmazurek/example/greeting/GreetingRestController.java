package pl.kamilmazurek.example.greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greetings")
public class GreetingRestController {

    private final Greeter greeter;

    public GreetingRestController(Greeter greeter) {
        this.greeter = greeter;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return greeter.createHelloMessage();
    }

}

