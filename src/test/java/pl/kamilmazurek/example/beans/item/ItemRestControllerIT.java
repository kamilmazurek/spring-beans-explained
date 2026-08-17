package pl.kamilmazurek.example.beans.item;

import org.junit.jupiter.api.Test;
import pl.kamilmazurek.example.beans.AbstractIT;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

class ItemRestControllerIT extends AbstractIT {

    @Test
    void shouldGetItem() {
        when()
                .get("/api/items/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(1))
                .body("name", equalTo("Item A"));
    }

    @Test
    void shouldNotFindItem() {
        when()
                .get("/api/items/999")
                .then()
                .statusCode(404);
    }

}