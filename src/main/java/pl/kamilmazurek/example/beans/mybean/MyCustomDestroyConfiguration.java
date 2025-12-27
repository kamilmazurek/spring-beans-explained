package pl.kamilmazurek.example.beans.mybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyCustomDestroyConfiguration {

    @Bean(destroyMethod = "customDestroy")
    public MyCustomDestroyBean myCustomDestroyBean() {
        return new MyCustomDestroyBean();
    }

}
