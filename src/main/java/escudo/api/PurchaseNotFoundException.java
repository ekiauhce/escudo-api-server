package escudo.api;

public class PurchaseNotFoundException extends Exception{
    public PurchaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
