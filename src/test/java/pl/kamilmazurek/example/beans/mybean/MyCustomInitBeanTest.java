package pl.kamilmazurek.example.beans.mybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class MyCustomInitBeanTest {

    @Test
    void shouldLogMessageWhenCustomInitIsInvoked(CapturedOutput output) {
        //given
        var myCustomInitBean = new MyCustomInitBean();

        //when
        myCustomInitBean.customInit();

        //then
        assertTrue(output.getOut().contains("Custom initialization method invoked"));
    }
}