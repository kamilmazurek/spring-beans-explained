package pl.kamilmazurek.example.beans.time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
