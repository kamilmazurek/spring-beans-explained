package pl.kamilmazurek.example.time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfiguration {

    @Bean
    public TimeProvider timeProvider() {
        return new TimeProvider();
    }

    @Bean
    public TimeLogger timeFormatter(TimeProvider timeProvider) {
        return new TimeLogger(timeProvider);
    }

}
