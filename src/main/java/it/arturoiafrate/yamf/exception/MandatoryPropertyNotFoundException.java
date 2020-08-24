package it.arturoiafrate.yamf.exception;

public class MandatoryPropertyNotFoundException extends GenericException {
    private final static String msg = "A mandatory property is not set: ";

    public MandatoryPropertyNotFoundException(String field) {
        super(msg.concat(field), ErrorCode.MANDATORY_NOT_FOUND);
    }
}
