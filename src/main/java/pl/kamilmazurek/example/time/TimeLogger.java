package pl.kamilmazurek.example.time;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TimeLogger {

    private final TimeProvider timeProvider;

    public TimeLogger(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public void logCurrentTime() {
        log.info("Current time: " + timeProvider.now());
    }

}
