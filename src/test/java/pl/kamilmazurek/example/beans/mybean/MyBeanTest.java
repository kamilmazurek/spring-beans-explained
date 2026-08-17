package pl.kamilmazurek.example.beans.mybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class MyBeanTest {

    @Test
    void shouldLogMessageWhenInstanceIsCreated(CapturedOutput output) {
        //when
        var myBean = new MyBean();

        //then
        assertNotNull(myBean);
        assertTrue(output.getOut().contains("MyBean instance created"));
    }
}