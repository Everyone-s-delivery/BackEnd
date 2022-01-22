package everyone.delivery.demo.common.exception;

public class AccessDeniedException extends NullPointerException{
    public AccessDeniedException(String msg) {
        super(msg);
    }

    public AccessDeniedException() {
        super();
    }
}
