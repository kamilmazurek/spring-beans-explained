package pl.kamilmazurek.example.greeter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
