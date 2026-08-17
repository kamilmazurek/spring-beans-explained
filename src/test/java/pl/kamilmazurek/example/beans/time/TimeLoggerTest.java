package pl.kamilmazurek.example.beans.time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class TimeLoggerTest {

    @Mock
    private TimeProvider timeProvider;

    @InjectMocks
    private TimeLogger timeLogger;

    @Test
    void shouldLogCurrentTime(CapturedOutput output) {
        //given
        var expectedTime = LocalDateTime.of(2026, 8, 17, 17, 20, 23);
        when(timeProvider.now()).thenReturn(expectedTime);

        //when
        timeLogger.logCurrentTime();

        //then
        verify(timeProvider).now();
        assertTrue(output.getOut().contains("Current time: " + expectedTime));
    }
}