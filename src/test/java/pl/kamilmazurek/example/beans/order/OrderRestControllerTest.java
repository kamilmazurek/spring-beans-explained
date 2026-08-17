package pl.kamilmazurek.example.beans.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderRestController orderRestController;

    @Test
    void shouldGetAllOrders() {
        //given
        var expectedOrders = List.of(new OrderEntity());
        when(orderService.getOrders()).thenReturn(expectedOrders);

        //when
        var actualOrders = orderRestController.getAllOrders();

        //then
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void shouldGetValidOrders() {
        //given
        var expectedValidOrders = List.of(new OrderEntity(), new OrderEntity());
        when(orderService.getValidOrders()).thenReturn(expectedValidOrders);

        //when
        var actualValidOrders = orderRestController.getValidOrders();

        //then
        assertEquals(expectedValidOrders, actualValidOrders);
    }

}