package pl.kamilmazurek.example.beans.order;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderValidatorTest {

    private final OrderValidator orderValidator = new OrderValidator();

    @Test
    void shouldReturnTrueWhenOrderHasProducts() {
        //given
        var order = new OrderEntity();
        order.setProducts(List.of("Product A"));

        //when & then
        assertTrue(orderValidator.isValid(order));
    }

    @Test
    void shouldReturnFalseWhenOrderIsNull() {
        assertFalse(orderValidator.isValid(null));
    }

    @Test
    void shouldReturnFalseWhenProductsAreNull() {
        //given
        var order = new OrderEntity();
        order.setProducts(null);

        //when & then
        assertFalse(orderValidator.isValid(order));
    }

    @Test
    void shouldReturnFalseWhenProductsAreEmpty() {
        //given
        var order = new OrderEntity();
        order.setProducts(Collections.emptyList());

        //when & then
        assertFalse(orderValidator.isValid(order));
    }

}