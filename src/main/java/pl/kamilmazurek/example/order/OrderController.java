package pl.kamilmazurek.example.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/valid")
    public ModelAndView showValidOrders() {
        return new ModelAndView("orders", "orders", orderService.getValidOrders());
    }

}
