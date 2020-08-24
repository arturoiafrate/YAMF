package it.arturoiafrate.yamf.exception;

public class IllegalAccessException extends GenericException{

    private final static String msg = "Can not access to the method definition.";

    public IllegalAccessException() {
        super(msg, ErrorCode.ILLEGAL_ACCESS);
    }

    public IllegalAccessException(Throwable cause) {
        super(msg, ErrorCode.ILLEGAL_ACCESS, cause);
    }
}
