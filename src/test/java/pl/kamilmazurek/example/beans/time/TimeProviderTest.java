package pl.kamilmazurek.example.beans.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeProviderTest {

    @Test
    void shouldReturnCurrentTime() {
        //given
        var timeProvider = new TimeProvider();
        var before = LocalDateTime.now().minusSeconds(1);

        //when
        var actualTime = timeProvider.now();

        //then
        var after = LocalDateTime.now().plusSeconds(1);

        assertNotNull(actualTime);
        assertTrue(actualTime.isAfter(before) && actualTime.isBefore(after));
    }
}