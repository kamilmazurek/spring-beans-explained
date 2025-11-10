package pl.kamilmazurek.example.whatisspringbean;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MyBean {

    public MyBean() {
        log.info("MyBean instance created");
    }

}
