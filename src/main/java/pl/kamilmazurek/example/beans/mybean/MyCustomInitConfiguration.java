package pl.kamilmazurek.example.beans.mybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyCustomInitConfiguration {

    @Bean(initMethod = "customInit")
    public MyCustomInitBean myCustomInitBean() {
        return new MyCustomInitBean();
    }

}