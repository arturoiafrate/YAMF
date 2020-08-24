package it.arturoiafrate.yamf.obj.impl;

import it.arturoiafrate.yamf.obj.IGenericObject;

import java.util.Optional;

public class GenericObject<T> implements IGenericObject {

    private final T object;

    public GenericObject(T obj){
        object = obj;
    }


    @Override
    public Optional<Object> getValue() {
        return Optional.ofNullable(object);
    }

    @Override
    public Class<?> getClassType() {
        return object.getClass();
    }

}
