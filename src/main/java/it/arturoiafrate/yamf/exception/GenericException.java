package it.arturoiafrate.yamf.exception;

public class GenericException extends Exception {

    private final ErrorCode code;

    public GenericException(ErrorCode code){
        super();
        this.code = code;
    }

    public GenericException(String message, ErrorCode code){
        super(message);
        this.code = code;
    }

    public GenericException(String message, ErrorCode code, Throwable cause){
        super(message, cause);
        this.code = code;
    }

    public ErrorCode getCode(){
        return this.code;
    }
}
