package pl.kamilmazurek.example.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderValidator orderValidator;

    private final OrderRepository orderRepository;

    public OrderService(OrderValidator orderValidator, OrderRepository orderRepository) {
        this.orderValidator = orderValidator;
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }

    public List<OrderEntity> getValidOrders() {
        return orderRepository.findAll().stream().filter(orderValidator::isValid).toList();
    }

}
