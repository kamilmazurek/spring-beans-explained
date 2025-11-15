package pl.kamilmazurek.example.time;

public class TimeFormatter {

    private final TimeProvider timeProvider;

    public TimeFormatter(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public void printCurrentTime() {
        System.out.println("Current time: " + timeProvider.now());
    }

}
