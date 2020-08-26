package it.arturoiafrate.yamf.field.setter.impl;

import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.exception.SubclassInitException;
import it.arturoiafrate.yamf.obj.IGenericObject;
import it.arturoiafrate.yamf.field.setter.IFieldSetter;
import it.arturoiafrate.yamf.exception.IllegalAccessException;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class FieldSetter implements IFieldSetter {

    private boolean set(Object object, String fieldName, Object fieldValue) throws GenericException {
        //1- If fieldName contains ".", we are going to look for a subclass
        //so we need to create a new instance of the nested class if it doesn't
        List<String> levels = Arrays.stream(fieldName.split("\\.")).collect(Collectors.toList());
        String realFieldName = initSubclasses(fieldName, object, levels);
        Object currentObj = object;
        Class<?> currentClass = object.getClass();
        while (currentClass != null) {
            try {
                while(levels.size()>0){//Going down to the nested class..
                    String level = levels.remove(0);
                    Field f = currentClass.getDeclaredField(level);
                    f.setAccessible(true);
                    currentObj = f.get(currentObj);
                    currentClass = currentObj.getClass();
                }
                //Applying the value to the field name
                Field field = currentClass.getDeclaredField(realFieldName);
                field.setAccessible(true);
                if(field.getType().equals(fieldValue.getClass()) || //Same class type...
                        (ClassUtils.isPrimitiveWrapper(fieldValue.getClass()) && //A primitive of this class type..
                                ClassUtils.wrapperToPrimitive(fieldValue.getClass()).equals(field.getType()))){
                    field.set(currentObj, fieldValue);
                    return true;
                }
                return false;
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            } catch (java.lang.IllegalStateException | java.lang.IllegalAccessException  e) {
                throw new IllegalAccessException(e);
            }
        }
        return false;
    }

    private String initSubclasses(String fieldName, Object object, List<String> levels) throws GenericException{
        String realFieldName = levels.remove(levels.size()-1);
        Object currentObj = object;
        for(String lvl: levels){
            try {
                Class<?> clazz = currentObj.getClass();
                Field field = clazz.getDeclaredField(lvl);
                field.setAccessible(true);
                Object nestedObj = field.get(currentObj);
                if(Optional.ofNullable(nestedObj).isEmpty()){
                    Class<?> nestedClazz = field.getType();
                    field.set(object, nestedClazz.getConstructor().newInstance());
                    currentObj = field;
                }
            } catch (Exception e) {
                throw new SubclassInitException(e);
            }
        }
        return realFieldName;
    }

    @Override
    public <T> T set(T obj, String fieldName, IGenericObject value) throws GenericException {
        set(obj, fieldName, extractValue(value.getValue()));
        return obj;
    }

    @Override
    public <T> T setAll(T obj, Map<String, IGenericObject> values) throws GenericException {
        AtomicReference<GenericException> ex = new AtomicReference<>(null);
        values.forEach((fieldName, fieldValue) -> {
            try {
                set(obj, fieldName, extractValue(fieldValue.getValue()));
            } catch (GenericException e){
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
