package pl.kamilmazurek.example.beans.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public ModelAndView showOrders() {
        var modelAndView = new ModelAndView("orders");

        modelAndView.addObject("name", "Orders");
        modelAndView.addObject("orders", orderService.getOrders());

        return modelAndView;
    }

    @GetMapping("/valid")
    public ModelAndView showValidOrders() {
        var modelAndView = new ModelAndView("orders");

        modelAndView.addObject("name", "Valid Orders");
        modelAndView.addObject("orders", orderService.getValidOrders());

        return modelAndView;
    }

}
