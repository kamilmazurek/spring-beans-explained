package pl.kamilmazurek.example.time;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogger {

    private final TimeProvider timeProvider;

    public TimeLogger(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public void logCurrentTime() {
        log.info("Current time: " + timeProvider.now());
    }

}
