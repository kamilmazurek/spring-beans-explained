package pl.kamilmazurek.example.beans.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void shouldShowOrders() {
        //given
        var order = new OrderEntity(1L, LocalDate.of(2025, 11, 15), 150.00, List.of("Product A", "Product B"));
        var expectedOrders = List.of(order);
        when(orderService.getOrders()).thenReturn(expectedOrders);

        //when
        var modelAndView = orderController.showOrders();

        //then
        assertEquals("orders", modelAndView.getViewName());
        assertEquals("Orders", modelAndView.getModel().get("name"));
        assertEquals(expectedOrders, modelAndView.getModel().get("orders"));
    }

    @Test
    void shouldShowValidOrders() {
        //given
        var order = new OrderEntity(2L, LocalDate.of(2025, 11, 16), 250.50, List.of("Product C", "Product D", "Product E"));
        var expectedOrders = List.of(order);
        when(orderService.getValidOrders()).thenReturn(expectedOrders);

        //when
        var modelAndView = orderController.showValidOrders();

        //then
        assertEquals("orders", modelAndView.getViewName());
        assertEquals("Valid Orders", modelAndView.getModel().get("name"));
        assertEquals(expectedOrders, modelAndView.getModel().get("orders"));
    }
}