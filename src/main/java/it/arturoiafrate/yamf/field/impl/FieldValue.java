package it.arturoiafrate.yamf.field.impl;

import it.arturoiafrate.yamf.field.IFieldValue;

import java.util.Optional;

public class FieldValue<T> implements IFieldValue {

    private final T object;

    public FieldValue(T obj){
        object = obj;
    }


    public Optional<Object> getValue() {
        return Optional.ofNullable(object);
    }

    public Class<?> getClassType() {
        return object.getClass();
    }
}
