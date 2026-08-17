package pl.kamilmazurek.example.beans.greeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GreetingRestControllerTest {

    @Mock
    private Greeter greeter;

    @InjectMocks
    private GreetingRestController greetingRestController;

    @Test
    void shouldSayHello() {
        //given
        var expectedMessage = "Mocked Hello!";
        when(greeter.createHelloMessage()).thenReturn(expectedMessage);

        //when
        var message = greetingRestController.sayHello();

        //then
        assertEquals(expectedMessage, message);
    }

}