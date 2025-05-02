package kg.attractor.edufood.exception.nsee;

import java.util.NoSuchElementException;

public class OrderNotFoundException extends NoSuchElementException {
    public OrderNotFoundException(){}
    public OrderNotFoundException(String message) {
        super(message);
    }
}
