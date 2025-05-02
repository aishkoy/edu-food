package kg.attractor.edufood.exception.nsee;

import java.util.NoSuchElementException;

public class RestaurantNotFoundException extends NoSuchElementException {
    public RestaurantNotFoundException(){}
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
