package kg.attractor.edufood.exception.iae;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
