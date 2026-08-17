package pl.kamilmazurek.example.beans.greeting;

import org.junit.jupiter.api.Test;
import pl.kamilmazurek.example.beans.AbstractIT;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

class GreetingRestControllerIT extends AbstractIT {

    @Test
    void shouldSayHello() {
        when()
                .get("/api/greetings/hello")
                .then()
                .statusCode(200)
                .assertThat()
                .body(equalTo("Hello!"));
    }

}