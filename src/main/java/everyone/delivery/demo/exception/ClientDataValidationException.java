package everyone.delivery.demo.exception;

public class ClientDataValidationException extends RuntimeException{

    public ClientDataValidationException(String msg, Throwable t) {
        super(msg, t);
    }

    public ClientDataValidationException(String msg) {
        super(msg);
    }

    public ClientDataValidationException() {
        super();
    }
}