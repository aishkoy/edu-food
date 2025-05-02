package kg.attractor.edufood.exception.nsee;

import java.util.NoSuchElementException;

public class OrderStatusNotFoundException extends NoSuchElementException {
    public OrderStatusNotFoundException(){}
    public OrderStatusNotFoundException(String message) {
        super(message);
    }
}
