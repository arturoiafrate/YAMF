package it.arturoiafrate.yamf.field.setter.impl;

import it.arturoiafrate.yamf.field.IFieldValue;
import it.arturoiafrate.yamf.field.setter.IFieldSetter;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public class FieldSetter implements IFieldSetter {


    private boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    @Override
    public <T> T set(T obj, String fieldName, IFieldValue value) {
        set(obj, fieldName, extractValue(value.getValue()));
        return obj;
    }

    @Override
    public <T> T setAll(T obj, Map<String, IFieldValue> values) {
        values.forEach((fieldName, fieldValue) -> set(obj, fieldName, extractValue(fieldValue.getValue())));
        return obj;
    }

    private Object extractValue(Optional<Object> encapsulated){
        return encapsulated.orElse(null);
    }
}
