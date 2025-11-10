package pl.kamilmazurek.example.greeter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreeterConfiguration {

    @Bean
    public Greeter greeter() {
        return new Greeter();
    }

}
