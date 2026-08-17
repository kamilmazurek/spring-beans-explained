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
class OrderServiceTest {

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldGetOrders() {
        //given
        var expectedOrders = List.of(new OrderEntity(), new OrderEntity());
        when(orderRepository.findAll()).thenReturn(expectedOrders);

        //when
        var actualOrders = orderService.getOrders();

        //then
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void shouldGetValidOrders() {
        //given
        var validOrder = new OrderEntity();
        validOrder.setId(1L);
        var invalidOrder = new OrderEntity();
        invalidOrder.setId(2L);
        var allOrders = List.of(validOrder, invalidOrder);

        when(orderRepository.findAll()).thenReturn(allOrders);
        when(orderValidator.isValid(validOrder)).thenReturn(true);
        when(orderValidator.isValid(invalidOrder)).thenReturn(false);

        //when
        var actualValidOrders = orderService.getValidOrders();

        //then
        assertEquals(1, actualValidOrders.size());
        assertEquals(validOrder, actualValidOrders.getFirst());
    }

}