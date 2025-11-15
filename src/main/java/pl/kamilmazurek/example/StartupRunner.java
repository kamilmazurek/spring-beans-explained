package pl.kamilmazurek.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.kamilmazurek.example.time.TimeFormatter;

@Component
public class StartupRunner implements ApplicationRunner {

    private final TimeFormatter timeFormatter;

    public StartupRunner(TimeFormatter timeFormatter) {
        this.timeFormatter = timeFormatter;
    }

    @Override
    public void run(ApplicationArguments args) {
        timeFormatter.printCurrentTime();
    }

}
