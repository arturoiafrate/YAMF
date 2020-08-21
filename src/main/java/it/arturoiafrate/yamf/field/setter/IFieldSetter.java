package it.arturoiafrate.yamf.field.setter;

import it.arturoiafrate.yamf.field.IFieldValue;

import java.util.Map;

public interface IFieldSetter {
    <T> T set(T obj, String fieldName, IFieldValue value);
    <T> T setAll(T obj, Map<String, IFieldValue> values);
}
