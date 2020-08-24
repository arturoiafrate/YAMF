package it.arturoiafrate.yamf.field.getter.impl;

import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.exception.IllegalAccessException;
import it.arturoiafrate.yamf.obj.impl.GenericObject;
import it.arturoiafrate.yamf.field.getter.IFieldGetter;
import it.arturoiafrate.yamf.obj.IGenericObject;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class FieldGetter<T> implements IFieldGetter {

    private final Map<String, IGenericObject> fieldList;

    public FieldGetter(T obj) throws IllegalAccessException {
        fieldList = new HashMap<>();
        Class<?> clazz = obj.getClass();
        AtomicReference<IllegalAccessException> ex = new AtomicReference<>(null);
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            try {
                addFieldInMap(obj, field);
            } catch (IllegalAccessException e) {
                ex.set(e);
            }
        });
        if(Optional.ofNullable(ex.get()).isPresent()) throw ex.get();
    }

    private void addFieldInMap(T obj, Field field) throws IllegalAccessException{
        field.setAccessible(true);
        try {
            IGenericObject value = new GenericObject<>(field.get(obj));
            fieldList.put(field.getName(), value);
        } catch (java.lang.IllegalAccessException e) {
            throw new IllegalAccessException(e);
        }
    }

    public Optional<Map<String, IGenericObject>> getAll() {
        return Optional.ofNullable(fieldList);
    }

    public Optional<IGenericObject> get(String key) {
        if(StringUtils.isEmpty(key) || fieldList.get(key) == null) return Optional.empty();
        return Optional.of(fieldList.get(key));
    }
}
