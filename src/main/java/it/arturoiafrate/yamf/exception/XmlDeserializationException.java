package it.arturoiafrate.yamf.exception;

public class XmlDeserializationException extends GenericException{
    private final static String msg = "Error while deserializing XML.";

    public  XmlDeserializationException(Throwable cause){ super(msg, ErrorCode.XML_EXCEPTION, cause); }
}
