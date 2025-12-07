package pl.kamilmazurek.example.beans.time;

import java.time.LocalDateTime;

public class TimeProvider {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

}