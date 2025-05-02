package kg.attractor.edufood.exception.nsee;

import java.util.NoSuchElementException;

public class ProductNotFoundException extends NoSuchElementException {
    public ProductNotFoundException() {}
    public ProductNotFoundException(String message) {
        super(message);
    }
}
