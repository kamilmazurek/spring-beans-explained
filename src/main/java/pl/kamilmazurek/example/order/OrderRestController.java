package pl.kamilmazurek.example.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderRestController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/api/orders")
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/api/orders/valid")
    public List<OrderEntity> getValidOrders() {
        return orderService.getValidOrders();
    }

}
