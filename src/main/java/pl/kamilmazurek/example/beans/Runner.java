package pl.kamilmazurek.example.beans;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.kamilmazurek.example.beans.time.TimeLogger;
import pl.kamilmazurek.example.beans.user.UserService;

@Component
@AllArgsConstructor
public class Runner implements ApplicationRunner {

    private final TimeLogger timeLogger;

    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        timeLogger.logCurrentTime();
        userService.logExistingUsers();
    }

}
