package kg.attractor.edufood.exception.nsee;

import java.util.NoSuchElementException;

public class ProductCategoryNotFoundException extends NoSuchElementException {
    public ProductCategoryNotFoundException() {}
    public ProductCategoryNotFoundException(String message) {
        super(message);
    }
}
