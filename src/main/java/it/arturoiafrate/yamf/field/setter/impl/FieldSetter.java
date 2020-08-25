package it.arturoiafrate.yamf.field.setter.impl;

import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.obj.IGenericObject;
import it.arturoiafrate.yamf.field.setter.IFieldSetter;
import it.arturoiafrate.yamf.exception.IllegalAccessException;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class FieldSetter implements IFieldSetter {


    private boolean set(Object object, String fieldName, Object fieldValue) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                if(field.getType().equals(fieldValue.getClass()) || //Same class type...
                        (ClassUtils.isPrimitiveWrapper(fieldValue.getClass()) && //A primitive of this class type..
                                ClassUtils.wrapperToPrimitive(fieldValue.getClass()).equals(field.getType()))
                ){
                    field.set(object, fieldValue);
                    return true;
                }
                return false;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (java.lang.IllegalStateException | java.lang.IllegalAccessException  e) {
                throw new IllegalAccessException(e);
            }
        }
        return false;
    }

    @Override
    public <T> T set(T obj, String fieldName, IGenericObject value) throws IllegalAccessException {
        set(obj, fieldName, extractValue(value.getValue()));
        return obj;
    }

    @Override
    public <T> T setAll(T obj, Map<String, IGenericObject> values) throws IllegalAccessException {
        AtomicReference<IllegalAccessException> ex = new AtomicReference<>(null);
        values.forEach((fieldName, fieldValue) -> {
            try {
                set(obj, fieldName, extractValue(fieldValue.getValue()));
            } catch (IllegalAccessException e){
                ex.set(e);
            }
        });
        if(Optional.ofNullable(ex.get()).isPresent()) throw ex.get();
        return obj;
    }

    private Object extractValue(Optional<Object> encapsulated){
        return encapsulated.orElse(null);
    }
}
