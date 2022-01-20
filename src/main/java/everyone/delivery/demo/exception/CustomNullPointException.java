package everyone.delivery.demo.exception;

public class CustomNullPointException extends NullPointerException{
    public CustomNullPointException(String msg) {
        super(msg);
    }

    public CustomNullPointException() {
        super();
    }
}
