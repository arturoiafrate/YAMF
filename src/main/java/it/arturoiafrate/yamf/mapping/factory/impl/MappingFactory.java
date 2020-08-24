package it.arturoiafrate.yamf.mapping.factory.impl;

import it.arturoiafrate.yamf.exception.CanNotInitializeClassException;
import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.exception.IllegalAccessException;
import it.arturoiafrate.yamf.exception.NoDefaultConstructorException;
import it.arturoiafrate.yamf.field.getter.impl.FieldGetter;
import it.arturoiafrate.yamf.field.setter.IFieldSetter;
import it.arturoiafrate.yamf.field.setter.impl.FieldSetter;
import it.arturoiafrate.yamf.obj.IGenericObject;
import it.arturoiafrate.yamf.obj.impl.GenericObject;
import it.arturoiafrate.yamf.mapping.factory.IMappingFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MappingFactory implements IMappingFactory{

    private IGenericObject source;
    private IGenericObject target;
    private Class<?> targetClass;

    @Override
    public <S> IMappingFactory fromObject(S object) {
        source = new GenericObject<>(object);
        return this;
    }

    @Override
    public <T> IMappingFactory toClass(Class<T> clazz) {
        targetClass = clazz;
        return this;
    }

    /*@Override
    public IMappingFactory convertAs(String fieldFrom, String fieldTo) {
        //TODO implementation...
        return this;
    }*/

    @Override
    public <T> T doConvert() throws GenericException {
        //1) Extracting field from soruce
        Map<String, IGenericObject> valueMap = extractFieldsFromSource();
        //2) Setting extracted fields from source to target
        setSTDValuesToTarget(valueMap);
        return (T) target.getValue().get();
    }

    private Map<String, IGenericObject> extractFieldsFromSource() throws IllegalAccessException {
        Map<String, IGenericObject> valueMap = new HashMap<>();
        if(source.getValue().isPresent()){
            Optional<Map<String, IGenericObject>> fields = new FieldGetter<>(source.getValue().get())
                    .getAll();
            if(fields.isPresent()) valueMap.putAll(fields.get());
        }
        return valueMap;
    }

    private void setSTDValuesToTarget(Map<String, IGenericObject> valueMap) throws GenericException{
        IFieldSetter setter = new FieldSetter();
        try {
            var d = targetClass.getDeclaredConstructor().newInstance();
            target = new GenericObject<>(setter.setAll(d, valueMap));
        } catch (InstantiationException e) {
            throw new CanNotInitializeClassException(e);
        } catch (java.lang.IllegalAccessException | InvocationTargetException e) {
            throw new IllegalAccessException(e);
        }  catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorException(e);
        }
    }
}
