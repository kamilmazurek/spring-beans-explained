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
    public TimeFormatter timeFormatter(TimeProvider timeProvider) {
        return new TimeFormatter(timeProvider);
    }

}
