package it.arturoiafrate.yamf.exception;

public class JsonDeserializationException extends GenericException{
    private final static String msg = "Error while deserializing JSON.";
    public JsonDeserializationException(Throwable cause){ super(msg, ErrorCode.JSON_EXCEPTION, cause); }
}
