package pl.kamilmazurek.example.beans.greeting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreeterTest {

    @Test
    void shouldCreateHelloMessage() {
        //given
        var greeter = new Greeter();

        //when
        var message = greeter.createHelloMessage();

        //then
        assertEquals("Hello!", message);
    }

}