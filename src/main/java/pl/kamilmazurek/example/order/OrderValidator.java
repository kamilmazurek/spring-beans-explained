package pl.kamilmazurek.example.order;

import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public boolean isValid(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return false;
        }

        if (hasNoProducts(orderEntity)) {
            return false;
        }

        return true;
    }

    private boolean hasNoProducts(OrderEntity orderEntity) {
        return orderEntity.getProducts() == null || orderEntity.getProducts().isEmpty();
    }

}