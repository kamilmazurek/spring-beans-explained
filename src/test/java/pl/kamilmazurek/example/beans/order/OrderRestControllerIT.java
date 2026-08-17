package pl.kamilmazurek.example.beans.order;

import org.junit.jupiter.api.Test;
import pl.kamilmazurek.example.beans.AbstractIT;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

class OrderRestControllerIT extends AbstractIT {

    @Test
    void shouldGetAllOrders() {
        when()
                .get("/api/orders")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", equalTo(3))
                .body("[0].id", equalTo(1))
                .body("[0].orderDate", equalTo("2025-11-15"))
                .body("[0].products", hasItems("Product A", "Product B"))
                .body("[1].id", equalTo(2))
                .body("[1].products", hasItems("Product C", "Product D", "Product E"));
    }

    @Test
    void shouldGetValidOrders() {
        when()
                .get("/api/orders/valid")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", equalTo(2))
                .body("[0].id", equalTo(1))
                .body("[1].id", equalTo(2));
    }
}