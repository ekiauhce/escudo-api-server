package escudo.api;

public class DuplicateProductException extends Exception {
    public DuplicateProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
