package it.arturoiafrate.yamf.field.getter.impl;

import it.arturoiafrate.yamf.field.impl.FieldValue;
import it.arturoiafrate.yamf.field.getter.IFieldGetter;
import it.arturoiafrate.yamf.field.IFieldValue;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FieldGetter<T> implements IFieldGetter {

    private final Map<String, IFieldValue> fieldList;

    public FieldGetter(T obj){
        fieldList = new HashMap<>();
        Class<?> clazz = obj.getClass();
        Arrays.stream(clazz.getDeclaredFields()).forEach(field ->{
            field.setAccessible(true);
            try {
                IFieldValue value = new FieldValue<>(field.get(obj));
                fieldList.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public Optional<Map<String, IFieldValue>> getAll() {
        return Optional.ofNullable(fieldList);
    }

    public Optional<IFieldValue> get(String key) {
        if(StringUtils.isEmpty(key) || fieldList.get(key) == null) return Optional.empty();
        return Optional.of(fieldList.get(key));
    }
}
