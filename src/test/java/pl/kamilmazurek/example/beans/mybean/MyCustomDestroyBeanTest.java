package pl.kamilmazurek.example.beans.mybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class MyCustomDestroyBeanTest {

    @Test
    void shouldLogMessageWhenCustomDestroyIsInvoked(CapturedOutput output) {
        //given
        var myCustomDestroyBean = new MyCustomDestroyBean();

        //when
        myCustomDestroyBean.customDestroy();

        //then
        assertTrue(output.getOut().contains("Custom destroy method invoked"));
    }

}