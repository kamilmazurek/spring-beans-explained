package pl.kamilmazurek.example.beans.mybean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCustomDestroyBean {

    public void customDestroy() {
        log.info("Custom destroy method invoked");
    }

}
