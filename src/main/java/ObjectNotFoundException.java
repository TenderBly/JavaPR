/**
 * Custom exception thrown when a target object cannot be found in the library collection.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}