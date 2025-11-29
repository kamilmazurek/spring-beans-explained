package pl.kamilmazurek.example.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/valid")
    public List<OrderEntity> getValidOrders() {
        return orderService.getValidOrders();
    }

}
