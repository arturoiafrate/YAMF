package it.arturoiafrate.yamf.exception;

public class SubclassInitException extends GenericException{
    private final static String msg = "Error while initializing nested classes.";

    public SubclassInitException(){
        super(msg, ErrorCode.SUBCLASS_INIT);
    }

    public SubclassInitException(Throwable cause) {
        super(msg, ErrorCode.SUBCLASS_INIT, cause);
    }
}
