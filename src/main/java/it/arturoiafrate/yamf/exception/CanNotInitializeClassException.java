package it.arturoiafrate.yamf.exception;

public class CanNotInitializeClassException extends GenericException {
    private final static String msg = "Can not initialize class.";

    public CanNotInitializeClassException() {
        super(msg, ErrorCode.NOT_INIT_CLASS);
    }
    public CanNotInitializeClassException(Throwable cause) {
        super(msg, ErrorCode.NOT_INIT_CLASS, cause);
    }
}
