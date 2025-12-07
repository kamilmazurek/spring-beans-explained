package pl.kamilmazurek.example.beans.mybean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCustomInitBean {

    public void customInit() {
        log.info("Custom initialization method invoked");
    }

}