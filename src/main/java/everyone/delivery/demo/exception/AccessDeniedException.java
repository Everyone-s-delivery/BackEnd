package everyone.delivery.demo.exception;

public class AccessDeniedException extends NullPointerException{
    public AccessDeniedException(String msg) {
        super(msg);
    }

    public AccessDeniedException() {
        super();
    }
}
