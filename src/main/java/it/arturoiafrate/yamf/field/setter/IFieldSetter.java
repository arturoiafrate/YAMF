package it.arturoiafrate.yamf.field.setter;

import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.obj.IGenericObject;

import java.util.Map;

public interface IFieldSetter {
    <T> T set(T obj, String fieldName, IGenericObject value) throws GenericException;
    <T> T setAll(T obj, Map<String, IGenericObject> values) throws GenericException;
}
