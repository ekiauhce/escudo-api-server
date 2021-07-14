package escudo.api.exceptions;

public class DuplicateProductException extends Exception {
    public DuplicateProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
