/**
 * Custom exception thrown when an invalid field value is passed to a constructor, setter, or method.
 */
public class InvalidFieldValueException extends RuntimeException {
    public InvalidFieldValueException(String message) {
        super(message);
    }
}