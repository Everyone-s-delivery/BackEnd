package everyone.delivery.demo.common.exception;

public class SignFailedException extends RuntimeException {
    public SignFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public SignFailedException(String msg) {
        super(msg);
    }

    public SignFailedException() {
        super();
    }
}
