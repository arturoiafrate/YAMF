package it.arturoiafrate.yamf.exception;

public class NoDefaultConstructorException extends GenericException {

    private final static String msg = "No default constructor defined for class.";

    public NoDefaultConstructorException() {
        super(msg, ErrorCode.NO_DEFAULT_CONSTRUCTOR);
    }

    public NoDefaultConstructorException(Throwable cause) {
        super(msg, ErrorCode.NO_DEFAULT_CONSTRUCTOR, cause);
    }

}
